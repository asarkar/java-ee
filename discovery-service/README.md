The server will start at `$DISCOVERY_HOST:$HTTP_PORT`

`DISCOVERY_HOST` is `localhost` by default.

`HTTP_PORT` is `8080` by default.

**To change the host and the port at runtime**:
```
docker run -it -p 8761:8761 \
-e DISCOVERY_HOST="$(echo $DOCKER_HOST | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}')" \
-e HTTP_PORT=8761 \
<CONTAINER ID>
```