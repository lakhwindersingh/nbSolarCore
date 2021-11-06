package com.neutronbinary.infectolabs.gateway.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class NBMapSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("nb_id", table, columnPrefix + "_nb_id"));
        columns.add(Column.aliased("nb_name", table, columnPrefix + "_nb_name"));
        columns.add(Column.aliased("nb_owner", table, columnPrefix + "_nb_owner"));
        columns.add(Column.aliased("nb_owner_private_key", table, columnPrefix + "_nb_owner_private_key"));
        columns.add(Column.aliased("nb_owner_public_key", table, columnPrefix + "_nb_owner_public_key"));
        columns.add(Column.aliased("nb_map_publish_method", table, columnPrefix + "_nb_map_publish_method"));
        columns.add(Column.aliased("nb_subscription_date", table, columnPrefix + "_nb_subscription_date"));
        columns.add(Column.aliased("nb_subscription_last_date", table, columnPrefix + "_nb_subscription_last_date"));
        columns.add(Column.aliased("nb_last_updated", table, columnPrefix + "_nb_last_updated"));
        columns.add(Column.aliased("nb_last_updated_by", table, columnPrefix + "_nb_last_updated_by"));

        return columns;
    }
}
