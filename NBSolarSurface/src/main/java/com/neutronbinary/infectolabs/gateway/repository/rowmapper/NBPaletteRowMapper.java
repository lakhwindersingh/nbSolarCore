package com.neutronbinary.infectolabs.gateway.repository.rowmapper;

import com.neutronbinary.infectolabs.gateway.domain.NBPalette;
import com.neutronbinary.infectolabs.gateway.service.ColumnConverter;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link NBPalette}, with proper type conversions.
 */
@Service
public class NBPaletteRowMapper implements BiFunction<Row, String, NBPalette> {

    private final ColumnConverter converter;

    public NBPaletteRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link NBPalette} stored in the database.
     */
    @Override
    public NBPalette apply(Row row, String prefix) {
        NBPalette entity = new NBPalette();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNbPaletteID(converter.fromRow(row, prefix + "_nb_palette_id", String.class));
        entity.setNbPaletteTitle(converter.fromRow(row, prefix + "_nb_palette_title", String.class));
        entity.setNbPaletteType(converter.fromRow(row, prefix + "_nb_palette_type", String.class));
        entity.setNbPaletteColors(converter.fromRow(row, prefix + "_nb_palette_colors", String.class));
        entity.setNbLastUpdated(converter.fromRow(row, prefix + "_nb_last_updated", String.class));
        entity.setNbLastUpdatedBy(converter.fromRow(row, prefix + "_nb_last_updated_by", String.class));
        return entity;
    }
}
