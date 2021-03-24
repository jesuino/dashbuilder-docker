package org.kie.dashbuilder.dashboards;

import java.nio.file.Paths;

import org.dashbuilder.displayer.DisplayerSettingsFactory;
import org.dashbuilder.dsl.factory.dashboard.DashboardBuilder;
import org.dashbuilder.dsl.factory.page.PageFactory;
import org.dashbuilder.dsl.model.Component;
import org.dashbuilder.dsl.model.Dashboard;
import org.dashbuilder.dsl.model.Navigation;
import org.dashbuilder.dsl.model.Page;
import org.kie.dashbuilder.DashboardGenerator;
import org.uberfire.ext.layout.editor.api.css.CssProperty;

import static java.util.Arrays.asList;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.external;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.externalBuilder;
import static org.dashbuilder.dsl.factory.dashboard.DashboardFactory.dashboard;
import static org.dashbuilder.dsl.factory.navigation.NavigationFactory.group;
import static org.dashbuilder.dsl.factory.navigation.NavigationFactory.item;
import static org.dashbuilder.dsl.factory.navigation.NavigationFactory.navigation;
import static org.dashbuilder.dsl.factory.page.PageFactory.page;
import static org.dashbuilder.dsl.factory.page.PageFactory.row;

public class SimpleExternalComponent implements DashboardGenerator {

    @Override
    public Dashboard build() {

        var componentsDir = this.getClass().getResource("/components").getFile();

        Component externalComponent = externalBuilder("very-simple").cssProperty(CssProperty.HEIGHT, "500px")
                                                                    .build();
        Page p1 = page("Page 1",
                       row(externalComponent));

        return dashboard(asList(p1), Paths.get(componentsDir));
    }

    @Override
    public String name() {
        return "SimpleExternalComponent";
    }

}
