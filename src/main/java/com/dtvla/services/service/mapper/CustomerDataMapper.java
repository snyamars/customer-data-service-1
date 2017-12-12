package com.dtvla.services.service.mapper;

import com.dtvla.services.domain.*;
import com.dtvla.services.service.dto.CustomerDataDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CustomerData and its DTO CustomerDataDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerDataMapper {

    CustomerDataDTO customerDataToCustomerDataDTO(CustomerData customerData);

    List<CustomerDataDTO> customerDataToCustomerDataDTOs(List<CustomerData> customerData);

    CustomerData customerDataDTOToCustomerData(CustomerDataDTO customerDataDTO);

    List<CustomerData> customerDataDTOsToCustomerData(List<CustomerDataDTO> customerDataDTOs);
}
