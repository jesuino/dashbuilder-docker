package org.kie.dashbuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.dashbuilder.dsl.serialization.DashboardExporter;
import org.dashbuilder.dsl.serialization.DashboardExporter.ExportType;
import org.kie.dashbuilder.dashboards.BrazilFuelPriceDashboard;
import org.kie.dashbuilder.dashboards.PokemonDashboard;

public class DashboardGeneratorRunner {

    private static final String MODELS_DIR_PROP = "models.dir";
    
    private static final String MODELS_DIR_DEFAULT = "/tmp/dashbuilder/models";

    private static final  List<DashboardGenerator> generators = List.of(new PokemonDashboard(), new BrazilFuelPriceDashboard());

    public static void main(String[] args) {
        String baseUrl = System.getProperty(MODELS_DIR_PROP, MODELS_DIR_DEFAULT);
        
        
        generators.forEach(g -> {
           Path dashboardPath = Paths.get(baseUrl, g.name() + ".zip");
           DashboardExporter.export(g.build(),
                                    dashboardPath.toString(),
                                    ExportType.ZIP);
        });

    }

}
