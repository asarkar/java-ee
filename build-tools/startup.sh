#!/bin/bash

DEFAULT_NUM_RETRIES=5
DEFAULT_RETRY_INTERVAL=5
DOCKER_HOST=${DOCKER_HOST:-127.0.0.1}
DISCOVERY_SVC_ADDR="$(echo $DOCKER_HOST | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'):8761"
SVC_IDS=
DEPENDENCIES=

usage() {
    cat << END
Usage: $0 [OPTIONS] <JAR PATH>

This script waits for a given number of services to come online.

OPTIONS:
  -h      Show this message.
  -n      Number of attempts. Optional. Defaults to $DEFAULT_NUM_RETRIES.
  -a      Comma-separated service ids to wait for. Optional.
  -d      Comma-separated dependencies to wait for. Optional. Format DEP_ADDR1:DEP_PORT1[,...,DEP_ADDRn:DEP_PORTn].
  -s      Discovery service host and port. Optional. Defaults to $DISCOVERY_SVC_ADDR.
  -i      Interval between the attempts. Optional. Defaults to $DEFAULT_RETRY_INTERVAL.
END
}

while getopts ":n::i::d::a::s::h" opt; do
  case ${opt} in
    n) NUM_RETRIES=$OPTARG;;
    i) RETRY_INTERVAL=$OPTARG;;
    d) DEPENDENCIES=$OPTARG;;
    a) SVC_IDS=$OPTARG;;
    s) DISCOVERY_SVC_ADDR=$OPTARG;;
    h) usage; exit 0;;
    \?) echo "[ERROR] Unknown option: -$OPTARG" >&2; usage; exit 1;;
    :) echo "[ERROR] Missing option argument for -$OPTARG" >&2; usage; exit 1;;
    *) echo "[ERROR] Unimplemented option: -$OPTARG" >&2; usage; exit 1;;
  esac
done

shift $((OPTIND - 1))

NUM_RETRIES=${NUM_RETRIES:-$(echo $DEFAULT_NUM_RETRIES)}
RETRY_INTERVAL=${RETRY_INTERVAL:-$(echo $DEFAULT_RETRY_INTERVAL)}
SVC_IDS=$(echo "$SVC_IDS" | sed -e 's/\s\+//g')
DEPENDENCIES=$(echo "$DEPENDENCIES" | sed -e 's/\s\+//g')

if [ -n "$SVC_IDS" ]; then
    printf "[INFO] Found service ids (%s).\n" "$SVC_IDS"

    IFS=',' read -a LIST <<< "$SVC_IDS"

    for SVC in "${LIST[@]}"; do
      SVC_ID=$(echo "$SVC" | sed -e 's/\s\+//g')

      STATUS=$(curl -s -o /dev/null -I -w "%{http_code}" "http://$DISCOVERY_SVC_ADDR/eureka/apps/$SVC_ID")

      COUNTER=$NUM_RETRIES

      while ((COUNTER > 0)) && ((STATUS > 400)); do
          printf "[INFO] Retrying connection to service (%s).\n" $SVC_ID
          STATUS=$(curl -s -o /dev/null -I -w "%{http_code}" "http://$DISCOVERY_SVC_ADDR/eureka/apps/$SVC_ID")

          ((COUNTER--))

          sleep $RETRY_INTERVAL
      done

      if ((STATUS > 400)); then
          printf "[ERROR] Failed to connect to service (%s) after %d seconds.\n" $SVC_ID "$((NUM_RETRIES * RETRY_INTERVAL))"

          exit 1
      else
          printf "[INFO] Connection to service (%s) is successful.\n" $SVC_ID
      fi
    done
fi

if [ -n "$DEPENDENCIES" ]; then
    printf "[INFO] Found dependencies (%s).\n" "$DEPENDENCIES"

    IFS=',' read -a LIST <<< "$DEPENDENCIES"

    for DEPENDENCY in "${LIST[@]}"; do
      DEPENDENCY=$(echo "$DEPENDENCY" | sed -e 's/\s\+//g')
      IFS=':' read -a DEP <<< "$DEPENDENCY"
      LEN=${#DEP[@]}

      if ((LEN < 2)); then
        usage; exit 1
      fi

      DEP_ADDR=${DEP[0]}
      DEP_PORT=${DEP[1]}

      if [ -z $DEP_ADDR ] || [ -z $DEP_PORT ]; then
        usage; exit 1
      fi

      nc -w5 -z $DEP_ADDR $DEP_PORT

      STATUS=$?

      COUNTER=$NUM_RETRIES

      while ((COUNTER > 0)) && ((STATUS > 0)); do
          printf "[INFO] Retrying connection to dependency (%s:%d).\n" $DEP_ADDR $DEP_PORT
          nc -w5 -z $DEP_ADDR $DEP_PORT > /dev/null 2>&1

          STATUS=$?
          ((COUNTER--))

          sleep $RETRY_INTERVAL
      done

      if ((STATUS > 0)); then
          printf "[ERROR] Failed to connect to dependency (%s:%d) after %d seconds.\n\n" $DEP_ADDR $DEP_PORT "$((NUM_RETRIES * RETRY_INTERVAL))"

          exit 1
      else
          printf "[INFO] Connection to dependency (%s:%d) is successful.\n" $DEP_ADDR $DEP_PORT
      fi
    done
fi

if [ -z "$@" ]; then
    printf "[ERROR] Nothing to run.\n\n"

    exit 1
fi

JAVA_OPTS="$JAVA_OPTS \
-Djava.security.egd=file:/dev/./urandom \
-Xdebug -Xrunjdwp:server=y,transport=dt_socket,suspend=${SUSPEND_ON_DEBUG:-n},address=${DEBUG_PORT:-8000}"

java -jar $JAVA_OPTS "$@"