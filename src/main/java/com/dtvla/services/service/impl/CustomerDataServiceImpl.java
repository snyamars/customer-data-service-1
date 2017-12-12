package com.dtvla.services.service.impl;

import com.dtvla.services.service.CustomerDataService;
import com.dtvla.services.domain.CustomerData;
import com.dtvla.services.repository.CustomerDataRepository;
import com.dtvla.services.service.dto.CustomerDataDTO;
import com.dtvla.services.service.mapper.CustomerDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CustomerData.
 */
@Service
@Transactional
public class CustomerDataServiceImpl implements CustomerDataService{

    private final Logger log = LoggerFactory.getLogger(CustomerDataServiceImpl.class);
    
    private final CustomerDataRepository customerDataRepository;

    private final CustomerDataMapper customerDataMapper;

    public CustomerDataServiceImpl(CustomerDataRepository customerDataRepository, CustomerDataMapper customerDataMapper) {
        this.customerDataRepository = customerDataRepository;
        this.customerDataMapper = customerDataMapper;
    }

    /**
     * Save a customerData.
     *
     * @param customerDataDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerDataDTO save(CustomerDataDTO customerDataDTO) {
        log.debug("Request to save CustomerData : {}", customerDataDTO);
        CustomerData customerData = customerDataMapper.customerDataDTOToCustomerData(customerDataDTO);
        customerData = customerDataRepository.save(customerData);
        CustomerDataDTO result = customerDataMapper.customerDataToCustomerDataDTO(customerData);
        return result;
    }

    /**
     *  Get all the customerData.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerData");
        Page<CustomerData> result = customerDataRepository.findAll(pageable);
        return result.map(customerData -> customerDataMapper.customerDataToCustomerDataDTO(customerData));
    }

    /**
     *  Get one customerData by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerDataDTO findOne(Long id) {
        log.debug("Request to get CustomerData : {}", id);
        CustomerData customerData = customerDataRepository.findOne(id);
        CustomerDataDTO customerDataDTO = customerDataMapper.customerDataToCustomerDataDTO(customerData);
        return customerDataDTO;
    }

    /**
     *  Delete the  customerData by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerData : {}", id);
        customerDataRepository.delete(id);
    }
}
