package com.neutronbinary.infectolabs.gateway.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class NBUserSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("nb_user_id", table, columnPrefix + "_nb_user_id"));
        columns.add(Column.aliased("nb_auth_type", table, columnPrefix + "_nb_auth_type"));
        columns.add(Column.aliased("nb_password_hash", table, columnPrefix + "_nb_password_hash"));
        columns.add(Column.aliased("nb_first_name", table, columnPrefix + "_nb_first_name"));
        columns.add(Column.aliased("nb_last_name", table, columnPrefix + "_nb_last_name"));
        columns.add(Column.aliased("nb_address", table, columnPrefix + "_nb_address"));
        columns.add(Column.aliased("nb_email_id", table, columnPrefix + "_nb_email_id"));
        columns.add(Column.aliased("nb_phone", table, columnPrefix + "_nb_phone"));
        columns.add(Column.aliased("nb_is_active", table, columnPrefix + "_nb_is_active"));
        columns.add(Column.aliased("nb_is_suspended", table, columnPrefix + "_nb_is_suspended"));
        columns.add(Column.aliased("nb_is_banished", table, columnPrefix + "_nb_is_banished"));
        columns.add(Column.aliased("nb_last_updated", table, columnPrefix + "_nb_last_updated"));
        columns.add(Column.aliased("nb_last_updated_by", table, columnPrefix + "_nb_last_updated_by"));

        return columns;
    }
}
