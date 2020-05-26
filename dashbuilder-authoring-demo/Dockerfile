FROM jboss/wildfly:18.0.1.Final

# Web Applications 
ADD dashbuilder-webapp.war /opt/jboss/wildfly/standalone/deployments/
ADD kie-server.war /opt/jboss/wildfly/standalone/deployments/

# Custom Configuration
ADD standalone-demo.xml /opt/jboss/wildfly/standalone/configuration

# Users
ADD application-users.properties /opt/jboss/wildfly/standalone/configuration/
ADD application-roles.properties /opt/jboss/wildfly/standalone/configuration/

# Kie Server Container 
ADD sample_kie_server.xml /opt/jboss/wildfly/

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-c", "standalone-demo.xml"]
