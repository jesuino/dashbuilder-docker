FROM jboss/wildfly:18.0.1.Final 
ADD dashbuilder-runtime.war /opt/jboss/wildfly/standalone/deployments/
RUN /opt/jboss/wildfly/bin/add-user.sh -a -u admin -p admin -g 'admin' --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
