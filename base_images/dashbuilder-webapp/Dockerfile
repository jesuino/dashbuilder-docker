FROM jboss/wildfly:19.1.0.Final 

ADD dashbuilder-webapp.war /opt/jboss/wildfly/standalone/deployments/

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
