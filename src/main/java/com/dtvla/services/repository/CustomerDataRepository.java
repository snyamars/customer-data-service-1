package com.dtvla.services.repository;

import com.dtvla.services.domain.CustomerData;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerData entity.
 */
@SuppressWarnings("unused")
public interface CustomerDataRepository extends JpaRepository<CustomerData,Long> {

}
