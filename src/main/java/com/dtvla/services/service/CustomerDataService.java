package com.dtvla.services.service;

import com.dtvla.services.service.dto.CustomerDataDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing CustomerData.
 */
public interface CustomerDataService {

    /**
     * Save a customerData.
     *
     * @param customerDataDTO the entity to save
     * @return the persisted entity
     */
    CustomerDataDTO save(CustomerDataDTO customerDataDTO);

    /**
     *  Get all the customerData.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerDataDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" customerData.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerDataDTO findOne(Long id);

    /**
     *  Delete the "id" customerData.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
