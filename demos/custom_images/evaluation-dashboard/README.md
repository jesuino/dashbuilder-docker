Evaluation Dashboard
--
A Runtime instance with a static dashboard.

This image starts Runtime with `dashboard.zip`, exported from Business Central. In Dockerfile we add this dashboard to the container:

```
# Custom Configuration
CP dashboard.zip /opt/jboss/
```

And in `conf/standalone-demo.xml` we configure Runtime to use this dashboard:
```
	<!-- Import Dashboard -->
	<property name="dashbuilder.runtime.import" value="/opt/jboss/dashboard.zip"/>
```

This image depends on Kie Server. See `demos/evaluation_dashboard_compose` for more information.

You can replace dashboard.zip by any dashboard exported from BC or Dashbuilder Webapp. 
