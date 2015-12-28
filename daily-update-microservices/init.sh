#!/bin/bash

WAIT_SCRIPT_URL=https://raw.githubusercontent.com/abhijitsarkar/docker/master/docker-util/wait.sh

curl -sSL -o ./wait.sh $WAIT_SCRIPT_URL
chmod +x ./wait.sh

DISCOVERY_ENDPOINT="$DISCOVERY_HOST:$DISCOVERY_PORT"
CONFIG_ENDPOINT="$CONFIG_HOST:$CONFIG_PORT"
DEPENDENCIES_STATUS=$(./wait.sh $DISCOVERY_ENDPOINT, $CONFIG_ENDPOINT)

rm -f ./wait.sh

if [ "$DEPENDENCIES_STATUS" -gt 0 ]; then
    printf "[ERROR] Could not connect to one or more dependencies (%s,%s).\n\n" $DISCOVERY_ENDPOINT $CONFIG_ENDPOINT

    exit 1
fi

supervisord -c /etc/supervisord.conf