package com.neutronbinary.infectolabs.gateway.repository.rowmapper;

import com.neutronbinary.infectolabs.gateway.domain.NBMap;
import com.neutronbinary.infectolabs.gateway.service.ColumnConverter;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link NBMap}, with proper type conversions.
 */
@Service
public class NBMapRowMapper implements BiFunction<Row, String, NBMap> {

    private final ColumnConverter converter;

    public NBMapRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link NBMap} stored in the database.
     */
    @Override
    public NBMap apply(Row row, String prefix) {
        NBMap entity = new NBMap();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNbID(converter.fromRow(row, prefix + "_nb_id", String.class));
        entity.setNbName(converter.fromRow(row, prefix + "_nb_name", String.class));
        entity.setNbOwner(converter.fromRow(row, prefix + "_nb_owner", String.class));
        entity.setNbOwnerPrivateKey(converter.fromRow(row, prefix + "_nb_owner_private_key", String.class));
        entity.setNbOwnerPublicKey(converter.fromRow(row, prefix + "_nb_owner_public_key", String.class));
        entity.setNbMapPublishMethod(converter.fromRow(row, prefix + "_nb_map_publish_method", String.class));
        entity.setNbSubscriptionDate(converter.fromRow(row, prefix + "_nb_subscription_date", String.class));
        entity.setNbSubscriptionLastDate(converter.fromRow(row, prefix + "_nb_subscription_last_date", String.class));
        entity.setNbLastUpdated(converter.fromRow(row, prefix + "_nb_last_updated", String.class));
        entity.setNbLastUpdatedBy(converter.fromRow(row, prefix + "_nb_last_updated_by", String.class));
        return entity;
    }
}
