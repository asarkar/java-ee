FROM abhijitsarkar/docker:tomcat8

ADD build/libs/*.war $CATALINA_HOME/webapps/discovery.war

CMD ["supervisord", "-c", "/etc/supervisord.conf"]