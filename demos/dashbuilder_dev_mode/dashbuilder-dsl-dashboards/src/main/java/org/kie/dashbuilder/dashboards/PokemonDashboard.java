package org.kie.dashbuilder.dashboards;

import java.util.Date;
import java.util.List;

import org.dashbuilder.dataset.def.DataSetDefFactory;
import org.dashbuilder.dataset.group.AggregateFunctionType;
import org.dashbuilder.displayer.DisplayerSettingsFactory;
import org.dashbuilder.displayer.Position;
import org.dashbuilder.dsl.model.Dashboard;
import org.kie.dashbuilder.DashboardGenerator;

import static org.dashbuilder.displayer.DisplayerSettingsFactory.newPieChartSettings;
import static org.dashbuilder.displayer.DisplayerSettingsFactory.newTableSettings;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.displayer;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.html;
import static org.dashbuilder.dsl.factory.dashboard.DashboardFactory.dashboard;
import static org.dashbuilder.dsl.factory.page.PageFactory.column;
import static org.dashbuilder.dsl.factory.page.PageFactory.page;
import static org.dashbuilder.dsl.factory.page.PageFactory.row;

public class PokemonDashboard implements DashboardGenerator {

    private static final String CSV_URL = "https://raw.githubusercontent.com/jesuino/dashbuilder-data/master/pokemon/pokemon.csv";

    @Override
    public Dashboard build() {
        var pokemonDs = DataSetDefFactory.newCSVDataSetDef()
                                         .uuid("pokemon_data")
                                         .fileURL(CSV_URL)
                                         .separatorChar(',')
                                         .quoteChar('"')
                                         .buildDef();
        var page = page("Pokemons Example",
                        row("<h1>Pokemon Dashboard - HEllo TDC</h1>"),
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

        return dashboard(List.of(page), List.of(pokemonDs));
    }

    @Override
    public String name() {
        return "pokemon";
    }

}
