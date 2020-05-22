FROM jboss/wildfly:18.0.1.Final

# Web Applications 
ADD dashbuilder-webapp.war /opt/jboss/wildfly/standalone/deployments/
ADD kie-server.war /opt/jboss/wildfly/standalone/deployments/


# Custom property
ADD standalone-demo.xml /opt/jboss/wildfly/standalone/configuration

# Kie Server Container 
ADD sample_kie_server.xml /opt/jboss/wildfly/

# User to manage Dashbuilder WebApp
RUN /opt/jboss/wildfly/bin/add-user.sh -a -u admin -p admin -g 'admin' --silent

# User to manage Kie Server
RUN /opt/jboss/wildfly/bin/add-user.sh -a -u kieserver -p kieserver1! -g 'admin,kie-server' --silent

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-c", "standalone-demo.xml"]
