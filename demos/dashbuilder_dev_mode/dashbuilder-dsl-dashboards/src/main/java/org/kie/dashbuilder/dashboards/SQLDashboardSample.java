package org.kie.dashbuilder.dashboards;

import java.util.List;

import org.dashbuilder.dataset.group.AggregateFunctionType;
import org.dashbuilder.dataset.sort.SortOrder;
import org.dashbuilder.dsl.factory.dashboard.DashboardBuilder;
import org.dashbuilder.dsl.factory.page.PageFactory;
import org.dashbuilder.dsl.model.Dashboard;
import org.kie.dashbuilder.DashboardGenerator;

import static java.util.Arrays.asList;
import static org.dashbuilder.dataset.def.DataSetDefFactory.newSQLDataSetDef;
import static org.dashbuilder.displayer.DisplayerSettingsFactory.newBarChartSettings;
import static org.dashbuilder.displayer.DisplayerSettingsFactory.newMetricSettings;
import static org.dashbuilder.displayer.DisplayerSettingsFactory.newSelectorSettings;
import static org.dashbuilder.displayer.DisplayerSettingsFactory.newTableSettings;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.displayer;
import static org.dashbuilder.dsl.factory.page.PageFactory.columnBuilder;
import static org.dashbuilder.dsl.factory.page.PageFactory.row;

public class SQLDashboardSample implements DashboardGenerator {

    @Override
    public Dashboard build() {

        // Sample SQL dashboard using database from: 
        // https://www.w3resource.com/sql/sql-table.php

        final String dataSourceName = "sample";

        var orders = newSQLDataSetDef().uuid("orders")
                                       .name("orders")
                                       .dataSource(dataSourceName)
                                       .dbTable("orders", true)
                                       .buildDef();

        var daysorder = newSQLDataSetDef().uuid("daysorder")
                                          .dataSource(dataSourceName)
                                          .dbTable("daysorder", true)
                                          .buildDef();

        var agents = newSQLDataSetDef().uuid("agents")
                                       .dataSource(dataSourceName)
                                       .dbTable("agents", true)
                                       .buildDef();

        var ordersSum = newMetricSettings().dataset(orders.getUUID())
                                           .title("Orders Amount Total")
                                           .titleVisible(true)
                                           .filterOn(false, false, true)
                                           .column("ord_amount", AggregateFunctionType.SUM)
                                           .htmlTemplate("<h4><strong>${title}:</strong> ${value}</h4>")
                                           .buildSettings();

        var agentFilter = newSelectorSettings().subType_Labels()
                                               .dataset(agents.getUUID())
                                               .group("agent_code")
                                               .column("agent_code")
                                               .column("agent_name")
                                               .multiple(true)
                                               .filterOn(false, true, false)
                                               .buildSettings();

        var ordersByAgentBarChart = newBarChartSettings().subType_Column()
                                                         .width(500)
                                                         .height(300)
                                                         .filterOn(false, false, true)
                                                         .title("All Time Orders")
                                                         .titleVisible(true)
                                                         .dataset(orders.getUUID())
                                                         .group("agent_code")
                                                         .column("agent_code", "Agent Code")
                                                         .column("ord_amount", AggregateFunctionType.SUM, "Orders Amount Sum")
                                                         .sort("ord_amount", SortOrder.DESCENDING)
                                                         .buildSettings();

        var daysOrderByAgentBarChart = newBarChartSettings().subType_Column()
                                                            .width(500)
                                                            .height(300)
                                                            .filterOn(false, false, true)
                                                            .title("Day's Order")
                                                            .titleVisible(true)
                                                            .dataset(daysorder.getUUID())
                                                            .group("agent_code")
                                                            .column("agent_code", "Agent Code")
                                                            .column("ord_amount", AggregateFunctionType.SUM, "Orders Amount Sum")
                                                            .sort("ord_amount", SortOrder.DESCENDING)
                                                            .buildSettings();

        var agentsDataTable = newTableSettings().resizable(true)
                                                .filterOn(false, false, true)
                                                .dataset(agents.getUUID())
                                                .tablePageSize(20)
                                                .tableOrderDefault("agent_code", SortOrder.ASCENDING)
                                                .column("AGENT_CODE")
                                                .column("AGENT_NAME")
                                                .column("WORKING_AREA")
                                                .column("COMMISSION")
                                                .column("PHONE_NO")
                                                .tableColumnPickerEnabled(false)
                                                .buildSettings();

        var page = PageFactory.pageBuilder("Order")
                              .property("margin-left", "220px")
                              .rows(row("<h3>Orders Amount by Agent</h3>"),

                                    row(displayer(agentFilter)),
                                    row("<hr />"),
                                    row(displayer(ordersSum)),
                                    row("<br />"),
                                    row(columnBuilder().component(displayer(ordersByAgentBarChart))
                                                       .property("width", "600px")
                                                       .build(),
                                        columnBuilder().component(displayer(daysOrderByAgentBarChart))
                                                       .property("width", "600px")
                                                       .build()),
                                    row("<h3>Agent Data</h3>"),
                                    row(columnBuilder().component(displayer(agentsDataTable))
                                                       .property("width", "1200px")
                                                       .build()))
                              .build();

        return DashboardBuilder.newBuilder(asList(page))
                               .dataSets(List.of(orders, daysorder, agents))
                               .build();
    }

    @Override
    public String name() {
        return "Orders";
    }

}