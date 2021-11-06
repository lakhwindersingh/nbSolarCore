package com.neutronbinary.infectolabs.microservice.repository;

import com.neutronbinary.infectolabs.microservice.domain.NBMapComponents;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NBMapComponents entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NBMapComponentsRepository extends JpaRepository<NBMapComponents, Long> {}
