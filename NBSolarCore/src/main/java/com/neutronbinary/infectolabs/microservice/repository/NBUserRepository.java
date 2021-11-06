package com.neutronbinary.infectolabs.microservice.repository;

import com.neutronbinary.infectolabs.microservice.domain.NBUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NBUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NBUserRepository extends JpaRepository<NBUser, Long> {}
