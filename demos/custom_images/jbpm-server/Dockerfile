FROM jboss/jbpm-server-full:7.48.0.Final

# Users
ADD ./conf/users.properties /opt/jboss/wildfly/standalone/configuration/
ADD ./conf/roles.properties /opt/jboss/wildfly/standalone/configuration/

# Controller Containers
ADD ./conf/.niogit /opt/jboss/wildfly/bin/.niogit

# Adding repository with evaluation container
ADD ./repository /opt/jboss/.m2/repository

# Fix permission
USER root
RUN chown -R jboss:jboss /opt/jboss/
USER jboss

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
