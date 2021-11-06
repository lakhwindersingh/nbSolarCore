package com.neutronbinary.infectolabs.gateway.repository.rowmapper;

import com.neutronbinary.infectolabs.gateway.domain.NBUser;
import com.neutronbinary.infectolabs.gateway.service.ColumnConverter;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link NBUser}, with proper type conversions.
 */
@Service
public class NBUserRowMapper implements BiFunction<Row, String, NBUser> {

    private final ColumnConverter converter;

    public NBUserRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link NBUser} stored in the database.
     */
    @Override
    public NBUser apply(Row row, String prefix) {
        NBUser entity = new NBUser();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNbUserID(converter.fromRow(row, prefix + "_nb_user_id", String.class));
        entity.setNbAuthType(converter.fromRow(row, prefix + "_nb_auth_type", String.class));
        entity.setNbPasswordHash(converter.fromRow(row, prefix + "_nb_password_hash", String.class));
        entity.setNbFirstName(converter.fromRow(row, prefix + "_nb_first_name", String.class));
        entity.setNbLastName(converter.fromRow(row, prefix + "_nb_last_name", String.class));
        entity.setNbAddress(converter.fromRow(row, prefix + "_nb_address", String.class));
        entity.setNbEmailId(converter.fromRow(row, prefix + "_nb_email_id", String.class));
        entity.setNbPhone(converter.fromRow(row, prefix + "_nb_phone", String.class));
        entity.setNbIsActive(converter.fromRow(row, prefix + "_nb_is_active", String.class));
        entity.setNbIsSuspended(converter.fromRow(row, prefix + "_nb_is_suspended", String.class));
        entity.setNbIsBanished(converter.fromRow(row, prefix + "_nb_is_banished", String.class));
        entity.setNbLastUpdated(converter.fromRow(row, prefix + "_nb_last_updated", String.class));
        entity.setNbLastUpdatedBy(converter.fromRow(row, prefix + "_nb_last_updated_by", String.class));
        return entity;
    }
}
