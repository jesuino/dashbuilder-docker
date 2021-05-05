package org.kie.dashbuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.dashbuilder.dsl.serialization.DashboardExporter;
import org.dashbuilder.dsl.serialization.DashboardExporter.ExportType;
import org.kie.dashbuilder.dashboards.BrazilFuelPriceDashboard;
import org.kie.dashbuilder.dashboards.NavigationWithMultiplePages;
import org.kie.dashbuilder.dashboards.PokemonDashboard;
import org.kie.dashbuilder.dashboards.PopulationExample;
import org.kie.dashbuilder.dashboards.SimpleExternalComponent;
import org.kie.dashbuilder.dashboards.SimplePrometheus;
import org.kie.dashbuilder.dashboards.TDCDemo;

public class DashboardGeneratorRunner {

    private static final String MODELS_DIR_PROP = "models.dir";

    private static final String MODELS_DIR_DEFAULT = DashboardGeneratorRunner.class.getResource(".").getFile();

    private static final DashboardGenerator[] generators = {
                                                            new PokemonDashboard(),
                                                            new BrazilFuelPriceDashboard(),
                                                            new PopulationExample(),
                                                            new NavigationWithMultiplePages(),
                                                            new SimpleExternalComponent(),
                                                            new TDCDemo(),
                                                            new SimplePrometheus()
    };

    public static void main(String[] args) {
        System.setProperty(MODELS_DIR_PROP, "/tmp/dashbuilder/models");
        String baseUrl = System.getProperty(MODELS_DIR_PROP, MODELS_DIR_DEFAULT);

        Arrays.asList(generators)
              .forEach(g -> {
                  Path dashboardPath = Paths.get(baseUrl, g.name() + ".zip");
                  System.out.println(dashboardPath);
                  DashboardExporter.get().export(g.build(),
                                                 dashboardPath.toString(),
                                                 ExportType.ZIP);
              });

    }

}
