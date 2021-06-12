package org.kie.dashbuilder.dashboards;

import java.util.List;

import org.dashbuilder.dsl.factory.dashboard.DashboardBuilder;
import org.dashbuilder.dsl.model.Dashboard;
import org.kie.dashbuilder.DashboardGenerator;

import static org.dashbuilder.dsl.factory.navigation.NavigationFactory.group;
import static org.dashbuilder.dsl.factory.navigation.NavigationFactory.item;
import static org.dashbuilder.dsl.factory.navigation.NavigationFactory.navigation;
import static org.dashbuilder.dsl.factory.page.PageFactory.page;
import static org.dashbuilder.dsl.factory.page.PageFactory.row;

public class NavigationWithMultiplePages implements DashboardGenerator {

    @Override
    public Dashboard build() {
        
        var p1 = page("Page 1",
                         row("<h3>This is page 1</h3>"));
        var p2 = page("Page 2",
                         row("<h3>This is page 2</h3>"));

        var navigation = navigation(group("Page 1 Navigation Group", item(p1)));

        return DashboardBuilder.newBuilder(List.of(p1, p2))
                               .navigation(navigation)
                               .build();
    }

    @Override
    public String name() {
        return "NavigationWithMultiplePages";
    }

}
