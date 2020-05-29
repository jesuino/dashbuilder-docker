Evaluation Dashboard
--
A Runtime instance with a static dashboard.

This image starts Runtime with `evaluation_dashboard.zip`, exported from Business Central. In Dockerfile we add this dashboard to the container:

```
# Custom Configuration
ADD ./evaluation_dashboard.zip /opt/jboss/
```

And in `conf/standalone-demo.xml` we configure Runtime to use this dashboard:
```
	<!-- Import Dashboard -->
	<property name="dashbuilder.runtime.import" value="/opt/jboss/evaluation_dashboard.zip"/>
```

This image depends on Kie Server. See `demos/evaluation_dashboard_compose` for more informatin.
