package org.kie.dashbuilder;

import java.util.Arrays;
import java.util.Date;

import org.dashbuilder.dataset.def.DataSetDef;
import org.dashbuilder.dataset.def.DataSetDefFactory;
import org.dashbuilder.dataset.group.AggregateFunctionType;
import org.dashbuilder.displayer.DisplayerSettingsFactory;
import org.dashbuilder.displayer.Position;
import org.dashbuilder.dsl.model.Dashboard;
import org.dashbuilder.dsl.model.Page;
import org.dashbuilder.dsl.serialization.DashboardExporter;
import org.dashbuilder.dsl.serialization.DashboardExporter.ExportType;

import static org.dashbuilder.displayer.DisplayerSettingsFactory.newPieChartSettings;
import static org.dashbuilder.displayer.DisplayerSettingsFactory.newTableSettings;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.displayer;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.html;
import static org.dashbuilder.dsl.factory.dashboard.DashboardFactory.dashboard;
import static org.dashbuilder.dsl.factory.page.PageFactory.column;
import static org.dashbuilder.dsl.factory.page.PageFactory.page;
import static org.dashbuilder.dsl.factory.page.PageFactory.row;

public class DashboardGenerator {

    private static final String CSV_URL = "https://raw.githubusercontent.com/jesuino/dashbuilder-data/master/pokemon/pokemon.csv";

    public static void main(String[] args) {

        Dashboard dashboard = pokemon();

        DashboardExporter.export(dashboard,
                                 "/home/wsiqueir/projects/dashbuilder-docker/demos/dashbuilder_dev_mode/models/pokemon.zip",
                                 ExportType.ZIP);

    }

    public static Dashboard pokemon() {
        DataSetDef pokemonDs = DataSetDefFactory.newCSVDataSetDef()
                                                .uuid("pokemon_data")
                                                .fileURL(CSV_URL)
                                                .separatorChar(',')
                                                .quoteChar('"')
                                                .buildDef();
        Page page = page("Pokemons Example",
                         row("<h1>Pokemon Dashboard</h1>"),
                         row("<hr />"),
                         row("<h3>All Data</h3>"),
                         row(displayer(newTableSettings().tableColumnPickerEnabled(false)
                                                         .dataset(pokemonDs.getUUID())
                                                         .column("name")
                                                         .column("attack")
                                                         .column("defense")
                                                         .column("hp")
                                                         .column("speed")
                                                         .column("type1")
                                                         .column("type2")
                                                         .column("generation")
                                                         .buildSettings())),
                         row("<h3>Pokemon by type</h3>"),
                         row(displayer(newPieChartSettings().legendOn(Position.RIGHT)
                                                            .dataset(pokemonDs.getUUID())
                                                            .group("type1")
                                                            .column("type1")
                                                            .column("type1",
                                                                    AggregateFunctionType.COUNT)
                                                            .buildSettings())),
                         row("<hr />"),
                         row("<h3>Average Stats by Type</h3>"),
                         row(displayer(DisplayerSettingsFactory.newBarChartSettings()
                                                               .width(1200)
                                                               .subType_Column()
                                                               .dataset(pokemonDs.getUUID())
                                                               .group("type1")
                                                               .column("type1")
                                                               .column("attack",
                                                                       AggregateFunctionType.AVERAGE,
                                                                       "Attack")
                                                               .column("defense",
                                                                       AggregateFunctionType.AVERAGE,
                                                                       "Defense")
                                                               .column("hp",
                                                                       AggregateFunctionType.AVERAGE,
                                                                       "HP")
                                                               .column("speed",
                                                                       AggregateFunctionType.AVERAGE,
                                                                       "Speed")
                                                               .buildSettings())),
                         row(column(html("<em> Generated at " + new Date() + "</em>"))));

        return dashboard(Arrays.asList(page), Arrays.asList(pokemonDs));
    }

}
