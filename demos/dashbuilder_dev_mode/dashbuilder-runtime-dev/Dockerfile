FROM quay.io/wsiqueir/dashbuilder-runtime:latest

RUN /opt/jboss/wildfly/bin/add-user.sh -a -u 'admin' -p 'admin' -g 'admin' 

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0" ]
