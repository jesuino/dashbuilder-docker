package org.kie.dashbuilder.dashboards;

import java.util.Arrays;
import java.util.UUID;

import org.dashbuilder.dataset.ColumnType;
import org.dashbuilder.dataset.DataSetFactory;
import org.dashbuilder.dataset.group.AggregateFunctionType;
import org.dashbuilder.displayer.DisplayerSettingsFactory;
import org.dashbuilder.dsl.factory.dashboard.DashboardFactory;
import org.dashbuilder.dsl.model.Dashboard;
import org.dashbuilder.dsl.model.Page;
import org.kie.dashbuilder.DashboardGenerator;

import static org.dashbuilder.dsl.factory.page.PageFactory.page;
import static org.dashbuilder.dsl.factory.page.PageFactory.row;

public class TDCDemoDashboard implements DashboardGenerator {

    @Override
    public Dashboard build() {

        var dataSet = DataSetFactory.newDataSetBuilder()
                                    .uuid(UUID.randomUUID().toString())
                                    .column("ID", ColumnType.LABEL)
                                    .column("AGE", ColumnType.NUMBER)
                                    .column("LANG", ColumnType.TEXT)
                                    .row("1", "25", "Java")
                                    .row("2", "32", "Java")
                                    .row("3", "22", "Javascript")
                                    .row("4", "48", "Cobol")
                                    .buildDataSet();

        var table = DisplayerSettingsFactory.newTableSettings().dataset(dataSet).buildSettings();

        var barChart = DisplayerSettingsFactory.newBarChartSettings().dataset(dataSet)
                .group("LANG")
                                               .column("LANG")
                                               .column("LANG", AggregateFunctionType.COUNT)
                                               .buildSettings();

        Page page = page("All Attendees", row("<h1>Participantes do TDC</h1>"), row(barChart), row(table));

        return DashboardFactory.dashboard(Arrays.asList(page));
    }

    @Override
    public String name() {
        return "tdc-demo";
    }

}
