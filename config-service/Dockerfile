FROM abhijitsarkar/docker:tomcat8

ADD build/libs/*.war $CATALINA_HOME/webapps/ROOT.war

CMD ["supervisord", "-c", "/etc/supervisord.conf"]