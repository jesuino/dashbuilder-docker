FROM quay.io/wsiqueir/dashbuilder-webapp

# Custom Configuration
ADD ./conf/standalone-demo.xml /opt/jboss/wildfly/standalone/configuration

# Users
ADD ./conf/application-users.properties /opt/jboss/wildfly/standalone/configuration/
ADD ./conf/application-roles.properties /opt/jboss/wildfly/standalone/configuration/

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-c", "standalone-demo.xml", "-Djboss.socket.binding.port-offset=100"]
