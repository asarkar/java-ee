The server will start at `$CONFIG_HOST:$HTTP_PORT`

`CONFIG_HOST` is `localhost` by default.

`HTTP_PORT` is `8080` by default.

**To change the host and the port at runtime**:
```
docker run -it -p 8888:8888 \
-e CONFIG_HOST="$(echo $DOCKER_HOST | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}')" \
-e HTTP_PORT=8888 \
-e DISCOVERY_HOST="$(echo $DOCKER_HOST | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}')" \
-e DISCOVERY_PORT=8761 \
<CONTAINER ID>
```