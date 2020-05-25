FROM quay.io/wsiqueir/dashbuilder-runtime 

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-Djboss.socket.binding.port-offset=100", "-Dorg.dashbuilder.kieserver.serverTemplate.sample_kie_server.location=http://localhost:8080/kie-server/services/rest/server", "-Dorg.dashbuilder.kieserver.serverTemplate.sample_kie_server.user=kieserver", "-Dorg.dashbuilder.kieserver.serverTemplate.sample_kie_server.password=kieserver1!"]
