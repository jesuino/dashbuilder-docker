FROM quay.io/wsiqueir/dashbuilder-runtime

# Custom Configuration
ADD ./conf/standalone-demo.xml /opt/jboss/wildfly/standalone/configuration

# Add Dashboard
COPY dashboard.zip /opt/jboss/dashboard.zip

# Users
ADD ./conf/application-users.properties /opt/jboss/wildfly/standalone/configuration/
ADD ./conf/application-roles.properties /opt/jboss/wildfly/standalone/configuration/

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-c", "standalone-demo.xml", "-Djboss.socket.binding.port-offset=300"]
