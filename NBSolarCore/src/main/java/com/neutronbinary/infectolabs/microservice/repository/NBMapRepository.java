package com.neutronbinary.infectolabs.microservice.repository;

import com.neutronbinary.infectolabs.microservice.domain.NBMap;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NBMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NBMapRepository extends JpaRepository<NBMap, Long> {}
