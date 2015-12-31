The server will start at `$DISCOVERY_HOST:$HTTP_PORT`

`DISCOVERY_HOST` is `localhost` by default.

`HTTP_PORT` is `8761` by default.

Minimal startup command:
```
docker run -it -p 8761:8761 \
-e DISCOVERY_HOST="$(echo $DOCKER_HOST | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}')" \
<CONTAINER ID>
```

Look in `build.gradle` for other available environment variables.