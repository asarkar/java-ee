#!/usr/bin/env bash

JAVA_OPTS="$JAVA_OPTS \
-Djava.security.egd=file:/dev/./urandom \
-Xdebug -Xrunjdwp:server=y,transport=dt_socket,suspend=${SUSPEND_ON_DEBUG:-n},address=${DEBUG_PORT:-8000}"

java -jar $JAVA_OPTS "$@"
