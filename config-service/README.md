The server will start at `$CONFIG_HOST:$HTTP_PORT`

`CONFIG_HOST` is `localhost` by default.

`HTTP_PORT` is `8888` by default.

Minimal startup command:
```
docker run -it -p 8888:8888 \
-e CONFIG_HOST="$(echo $DOCKER_HOST | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}')" \
-e DISCOVERY_HOST="$(echo $DOCKER_HOST | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}')" \
-e ENCRYPT_KEY=<secret>
<CONTAINER ID>
```

**An encrypt key must be provided. Note that full-strength JCE must be installed.**

Look in `build.gradle` for other available environment variables.