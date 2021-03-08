package org.kie.dashbuilder.dashboards;

import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;

import org.dashbuilder.dataset.ColumnType;
import org.dashbuilder.dataset.def.DataSetDefFactory;
import org.dashbuilder.dataset.group.AggregateFunctionType;
import org.dashbuilder.dataset.sort.SortOrder;
import org.dashbuilder.displayer.DisplayerSubType;
import org.dashbuilder.dsl.model.Column;
import org.dashbuilder.dsl.model.Dashboard;
import org.kie.dashbuilder.DashboardGenerator;

import static org.dashbuilder.dataset.filter.FilterFactory.equalsTo;
import static org.dashbuilder.displayer.DisplayerSettingsFactory.newLineChartSettings;
import static org.dashbuilder.displayer.DisplayerSettingsFactory.newSelectorSettings;
import static org.dashbuilder.displayer.DisplayerSettingsFactory.newTableSettings;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.displayer;
import static org.dashbuilder.dsl.factory.dashboard.DashboardFactory.dashboard;
import static org.dashbuilder.dsl.factory.page.PageFactory.columnBuilder;
import static org.dashbuilder.dsl.factory.page.PageFactory.pageBuilder;
import static org.dashbuilder.dsl.factory.page.PageFactory.row;

public class BrazilFuelPriceDashboard implements DashboardGenerator {

    private static final String CSV_URL = "https://github.com/jesuino/data-etl/raw/main/preco_combustiveis/processed.csv";

    @Override
    public Dashboard build() {
        var YEAR = "YEAR";
        var fuelData = DataSetDefFactory.newCSVDataSetDef()
                                        .uuid("brazil_fuel")
                                        .fileURL(CSV_URL)
                                        .separatorChar(',')
                                        .quoteChar('"')
                                        .column(YEAR, ColumnType.LABEL)
                                        .column("STATE", ColumnType.LABEL)
                                        .buildDef();

        BiFunction<String, String, Column> priceColumn =
                (title, type) -> columnBuilder("1",
                                               displayer(newLineChartSettings().subType_SmoothLine()
                                                                               .dataset(fuelData.getUUID())
                                                                               .width(260)
                                                                               .height(200)
                                                                               .title(title)
                                                                               .margins(0, 0, 20, 0)
                                                                               .titleVisible(true)
                                                                               .yAxisShowLabels(false)
                                                                               .filterOn(false, false, true)
                                                                               .group(YEAR)
                                                                               .column(YEAR)
                                                                               .column("AVG", AggregateFunctionType.AVERAGE, title + " - Média")
                                                                               .sort(YEAR, SortOrder.ASCENDING)
                                                                               .filter(equalsTo("TYPE", type))
                                                                               .format(YEAR, "Ano", "#")
                                                                               .buildSettings()))
                                                                                                 .property("width", "270px")
                                                                                                 .build();

        var page = pageBuilder("Visão Geral").property("margin-left", "20px")
                                             .rows(
                                                   row("<h1>Preço dos Combustíveis</h1>"),
                                                   row("<hr />"),
                                                   row("<h2>Resumo</h2>"),
                                                   row(displayer(newSelectorSettings().subtype(DisplayerSubType.SELECTOR_DROPDOWN)
                                                                                      .multiple(false)
                                                                                      .dataset(fuelData.getUUID())
                                                                                      .filterOn(false, true, false)
                                                                                      .group("STATE")
                                                                                      .column("STATE")
                                                                                      .buildSettings())),
                                                   row(priceColumn.apply("Gasolina", "GASOLINA"),
                                                       priceColumn.apply("Diesel", "DIESEL"),
                                                       priceColumn.apply("Etanol", "ETANOL"),
                                                       priceColumn.apply("Diesel S10", "DIESEL S10"),
                                                       priceColumn.apply("GNV", "GNV")),
                                                   row("<hr/>"),
                                                   row("<h2>Todos dados</h2>"),
                                                   row(displayer(newTableSettings().dataset(fuelData.getUUID())
                                                                                   .buildSettings())),
                                                   row("<em> Generated at " + new Date() + "</em>")).build();

        return dashboard(List.of(page), List.of(fuelData));
    }

    @Override
    public String name() {
        return "preco_combustivel";
    }

}