package org.kie.dashbuilder;

import org.dashbuilder.dsl.model.Dashboard;

public interface DashboardGenerator {

    Dashboard build();

    String name();

}