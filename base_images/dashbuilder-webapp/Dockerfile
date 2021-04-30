FROM jboss/wildfly:19.1.0.Final 

# adds default admin user
RUN /opt/jboss/wildfly/bin/add-user.sh -a -u 'admin' -p 'admin' -g 'admin' 

ADD dashbuilder-webapp.war /opt/jboss/wildfly/standalone/deployments/

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
