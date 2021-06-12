package org.kie.dashbuilder.dashboards;

import java.nio.file.Paths;

import org.dashbuilder.dsl.model.Component;
import org.dashbuilder.dsl.model.Dashboard;
import org.dashbuilder.dsl.model.Page;
import org.kie.dashbuilder.DashboardGenerator;
import org.uberfire.ext.layout.editor.api.css.CssProperty;

import static java.util.Arrays.asList;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.externalBuilder;
import static org.dashbuilder.dsl.factory.dashboard.DashboardFactory.dashboard;
import static org.dashbuilder.dsl.factory.page.PageFactory.page;
import static org.dashbuilder.dsl.factory.page.PageFactory.row;

public class FunctionCallSample implements DashboardGenerator {

    @Override
    public Dashboard build() {
        var componentsDir = this.getClass().getResource("/components").getFile();

        Component externalComponent = externalBuilder("function-call").cssProperty(CssProperty.HEIGHT, "500px")
                                                                    .build();
        Page p1 = page("Page 1",
                       row(externalComponent));

        return dashboard(asList(p1), Paths.get(componentsDir));
    }

    @Override
    public String name() {
        return "FunctionTest";
    }

}
