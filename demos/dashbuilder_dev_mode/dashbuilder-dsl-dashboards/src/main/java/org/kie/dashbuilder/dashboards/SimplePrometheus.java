package org.kie.dashbuilder.dashboards;

import java.util.UUID;

import org.dashbuilder.dataset.def.DataSetDef;
import org.dashbuilder.dataset.def.DataSetDefFactory;
import org.dashbuilder.dataset.group.AggregateFunctionType;
import org.dashbuilder.displayer.DisplayerSettings;
import org.dashbuilder.displayer.DisplayerSettingsFactory;
import org.dashbuilder.dsl.factory.page.PageFactory;
import org.dashbuilder.dsl.model.Dashboard;
import org.dashbuilder.dsl.model.Page;
import org.kie.dashbuilder.DashboardGenerator;

import static java.util.Arrays.asList;
import static org.dashbuilder.dsl.factory.dashboard.DashboardFactory.dashboard;
import static org.dashbuilder.dsl.factory.page.PageFactory.row;

public class SimplePrometheus implements DashboardGenerator {

    @Override
    public Dashboard build() {

        DataSetDef prometheus = DataSetDefFactory.newPrometheusDataSetDef()
                                                 .uuid(UUID.randomUUID().toString())
                                                 .serverUrl("http://localhost:9090")
                                                 .query("prometheus_http_requests_total")
                                                 .buildDef();

        DisplayerSettings settings = DisplayerSettingsFactory.newBarChartSettings()
                                                             .subType_Column()
                                                             .dataset(prometheus.getUUID())
                                                             .group("handler")
                                                             .column("handler")
                                                             .column("VALUE", AggregateFunctionType.SUM)
                                                             .refreshOn(1, false)
                                                             .margins(0, 0, 60, 0)
                                                             .buildSettings();

        Page page = PageFactory.page("p1", row("<h1>Http Requests</h1>"), row(settings));
        return dashboard(asList(page), asList(prometheus));
    }

    @Override
    public String name() {
        return "simple-prometheus";
    }

}
