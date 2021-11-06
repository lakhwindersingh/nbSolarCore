package com.neutronbinary.infectolabs.gateway.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class NBPaletteSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("nb_palette_id", table, columnPrefix + "_nb_palette_id"));
        columns.add(Column.aliased("nb_palette_title", table, columnPrefix + "_nb_palette_title"));
        columns.add(Column.aliased("nb_palette_type", table, columnPrefix + "_nb_palette_type"));
        columns.add(Column.aliased("nb_palette_colors", table, columnPrefix + "_nb_palette_colors"));
        columns.add(Column.aliased("nb_last_updated", table, columnPrefix + "_nb_last_updated"));
        columns.add(Column.aliased("nb_last_updated_by", table, columnPrefix + "_nb_last_updated_by"));

        return columns;
    }
}
