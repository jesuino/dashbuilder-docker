FROM quay.io/wsiqueir/dashbuilder-runtime:latest

RUN /opt/jboss/wildfly/bin/add-user.sh -a -u 'admin' -p 'admin' -g 'admin' 

ENV LANG en_US.UTF-8

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0" ]
