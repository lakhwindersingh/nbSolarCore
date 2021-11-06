package com.neutronbinary.infectolabs.gateway.repository.rowmapper;

import com.neutronbinary.infectolabs.gateway.domain.NBChart;
import com.neutronbinary.infectolabs.gateway.service.ColumnConverter;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link NBChart}, with proper type conversions.
 */
@Service
public class NBChartRowMapper implements BiFunction<Row, String, NBChart> {

    private final ColumnConverter converter;

    public NBChartRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link NBChart} stored in the database.
     */
    @Override
    public NBChart apply(Row row, String prefix) {
        NBChart entity = new NBChart();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNbChartID(converter.fromRow(row, prefix + "_nb_chart_id", String.class));
        entity.setNbChartTitle(converter.fromRow(row, prefix + "_nb_chart_title", String.class));
        entity.setNbChartType(converter.fromRow(row, prefix + "_nb_chart_type", String.class));
        entity.setNbChartParams(converter.fromRow(row, prefix + "_nb_chart_params", String.class));
        entity.setNbLastUpdated(converter.fromRow(row, prefix + "_nb_last_updated", String.class));
        entity.setNbLastUpdatedBy(converter.fromRow(row, prefix + "_nb_last_updated_by", String.class));
        return entity;
    }
}
