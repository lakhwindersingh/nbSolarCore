package com.neutronbinary.infectolabs.microservice.repository;

import com.neutronbinary.infectolabs.microservice.domain.NBMapAttributes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NBMapAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NBMapAttributesRepository extends JpaRepository<NBMapAttributes, Long> {}
