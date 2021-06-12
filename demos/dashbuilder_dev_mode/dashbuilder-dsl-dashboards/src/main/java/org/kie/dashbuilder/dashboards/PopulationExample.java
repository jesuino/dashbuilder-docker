package org.kie.dashbuilder.dashboards;

import org.dashbuilder.dataset.ColumnType;
import org.dashbuilder.dsl.factory.component.ComponentFactory;
import org.dashbuilder.dsl.factory.dashboard.DashboardBuilder;
import org.dashbuilder.dsl.model.Dashboard;
import org.kie.dashbuilder.DashboardGenerator;

import static java.util.Arrays.asList;
import static org.dashbuilder.dataset.DataSetFactory.newDataSetBuilder;
import static org.dashbuilder.displayer.DisplayerSettingsFactory.newBarChartSettings;
import static org.dashbuilder.dsl.factory.navigation.NavigationFactory.group;
import static org.dashbuilder.dsl.factory.navigation.NavigationFactory.item;
import static org.dashbuilder.dsl.factory.navigation.NavigationFactory.navigation;
import static org.dashbuilder.dsl.factory.page.PageFactory.page;
import static org.dashbuilder.dsl.factory.page.PageFactory.row;

public class PopulationExample implements DashboardGenerator {

    @Override
    public Dashboard build() {
        var dataSet = newDataSetBuilder().column("Country", ColumnType.LABEL)
                                         .column("Population", ColumnType.NUMBER)
                                         .row("Brazil", "211")
                                         .row("United States", "328")
                                         .row("Cuba", "11")
                                         .row("India", "1366")
                                         .row("China", "1398")
                                         .buildDataSet();

        var populationBar = newBarChartSettings().subType_Column()
                                                 .width(800)
                                                 .height(600)
                                                 .dataset(dataSet)
                                                 .column("Country")
                                                 .column("Population")
                                                 .buildSettings();
        var page = page("Countries Population",
                        row("<h3> Countries Population</h3>"),
                        row(ComponentFactory.displayer(populationBar)));

        var navigation = navigation(group("Countries Information", item(page)));

        return DashboardBuilder.newBuilder(asList(page))
                               .navigation(navigation)
                               .build();
    }

    @Override
    public String name() {
        return "Population";
    }

}
