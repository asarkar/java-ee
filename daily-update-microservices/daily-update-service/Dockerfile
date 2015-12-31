FROM abhijitsarkar/docker:jdk8

ENV INIT_SCRIPT=/usr/local/bin/daily-update-service-startup.sh APP_HOME=/opt/name/abhijitsarkar/java-ee

ADD https://raw.githubusercontent.com/abhijitsarkar/java-ee/master/build-tools/startup.sh $INIT_SCRIPT

RUN chmod +x $INIT_SCRIPT

WORKDIR $APP_HOME

ADD build/libs/*.jar daily-update-service.jar

ENTRYPOINT ["/usr/local/bin/daily-update-service-startup.sh"]
CMD ["/opt/name/abhijitsarkar/java-ee/daily-update-service.jar"]