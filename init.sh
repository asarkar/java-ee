#!/bin/bash

# https://docs.docker.com/v1.8/userguide/dockerlinks/
# https://docs.docker.com/compose/env/

apt-get update && \
	apt-get -y install netcat

nc -w5 -z -n $DB_PORT_3306_TCP_ADDR $DB_PORT_3306_TCP_PORT

STATUS=$?

DEFAULT_NUM_RETRY=5
DEFAULT_RETRY_INTERVAL=5

NUM_RETRY=${NUM_RETRY:-$(echo $DEFAULT_NUM_RETRY)}
RETRY_INTERVAL=${RETRY_INTERVAL:-$(echo $DEFAULT_RETRY_INTERVAL)}

COUNTER=$NUM_RETRY

while [ $COUNTER -gt 0 -a $STATUS -gt 0 ]; do
    nc -w5 -z -n $DB_PORT_3306_TCP_ADDR $DB_PORT_3306_TCP_PORT

    STATUS=$?
    COUNTER=$((COUNTER - 1))

    sleep $RETRY_INTERVAL
done

if [ $STATUS -gt 0 ]; then
    printf "[ERROR] Failed to connect to the database after %d seconds.\n" $((NUM_RETRY * RETRY_INTERVAL))

    exit 1
fi

supervisord -c /etc/supervisord.conf