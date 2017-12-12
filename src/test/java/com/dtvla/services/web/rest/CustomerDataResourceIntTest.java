package com.dtvla.services.web.rest;

import com.dtvla.services.CustomerDataServiceApp;

import com.dtvla.services.domain.CustomerData;
import com.dtvla.services.repository.CustomerDataRepository;
import com.dtvla.services.service.CustomerDataService;
import com.dtvla.services.service.dto.CustomerDataDTO;
import com.dtvla.services.service.mapper.CustomerDataMapper;
import com.dtvla.services.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CustomerDataResource REST controller.
 *
 * @see CustomerDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerDataServiceApp.class)
public class CustomerDataResourceIntTest {

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    @Autowired
    private CustomerDataRepository customerDataRepository;

    @Autowired
    private CustomerDataMapper customerDataMapper;

    @Autowired
    private CustomerDataService customerDataService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerDataMockMvc;

    private CustomerData customerData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerDataResource customerDataResource = new CustomerDataResource(customerDataService);
        this.restCustomerDataMockMvc = MockMvcBuilders.standaloneSetup(customerDataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerData createEntity(EntityManager em) {
        CustomerData customerData = new CustomerData()
                .CustomerId(DEFAULT_CUSTOMER_ID);
        return customerData;
    }

    @Before
    public void initTest() {
        customerData = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerData() throws Exception {
        int databaseSizeBeforeCreate = customerDataRepository.findAll().size();

        // Create the CustomerData
        CustomerDataDTO customerDataDTO = customerDataMapper.customerDataToCustomerDataDTO(customerData);

        restCustomerDataMockMvc.perform(post("/api/customer-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDataDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerData in the database
        List<CustomerData> customerDataList = customerDataRepository.findAll();
        assertThat(customerDataList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerData testCustomerData = customerDataList.get(customerDataList.size() - 1);
        assertThat(testCustomerData.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    public void createCustomerDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerDataRepository.findAll().size();

        // Create the CustomerData with an existing ID
        CustomerData existingCustomerData = new CustomerData();
        existingCustomerData.setId(1L);
        CustomerDataDTO existingCustomerDataDTO = customerDataMapper.customerDataToCustomerDataDTO(existingCustomerData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerDataMockMvc.perform(post("/api/customer-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCustomerDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CustomerData> customerDataList = customerDataRepository.findAll();
        assertThat(customerDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomerData() throws Exception {
        // Initialize the database
        customerDataRepository.saveAndFlush(customerData);

        // Get all the customerDataList
        restCustomerDataMockMvc.perform(get("/api/customer-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerData.getId().intValue())))
            .andExpect(jsonPath("$.[*].CustomerId").value(hasItem(DEFAULT_CUSTOMER_ID)));
    }

    @Test
    @Transactional
    public void getCustomerData() throws Exception {
        // Initialize the database
        customerDataRepository.saveAndFlush(customerData);

        // Get the customerData
        restCustomerDataMockMvc.perform(get("/api/customer-data/{id}", customerData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerData.getId().intValue()))
            .andExpect(jsonPath("$.CustomerId").value(DEFAULT_CUSTOMER_ID));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerData() throws Exception {
        // Get the customerData
        restCustomerDataMockMvc.perform(get("/api/customer-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerData() throws Exception {
        // Initialize the database
        customerDataRepository.saveAndFlush(customerData);
        int databaseSizeBeforeUpdate = customerDataRepository.findAll().size();

        // Update the customerData
        CustomerData updatedCustomerData = customerDataRepository.findOne(customerData.getId());
        updatedCustomerData
                .CustomerId(UPDATED_CUSTOMER_ID);
        CustomerDataDTO customerDataDTO = customerDataMapper.customerDataToCustomerDataDTO(updatedCustomerData);

        restCustomerDataMockMvc.perform(put("/api/customer-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDataDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerData in the database
        List<CustomerData> customerDataList = customerDataRepository.findAll();
        assertThat(customerDataList).hasSize(databaseSizeBeforeUpdate);
        CustomerData testCustomerData = customerDataList.get(customerDataList.size() - 1);
        assertThat(testCustomerData.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerData() throws Exception {
        int databaseSizeBeforeUpdate = customerDataRepository.findAll().size();

        // Create the CustomerData
        CustomerDataDTO customerDataDTO = customerDataMapper.customerDataToCustomerDataDTO(customerData);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerDataMockMvc.perform(put("/api/customer-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDataDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerData in the database
        List<CustomerData> customerDataList = customerDataRepository.findAll();
        assertThat(customerDataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerData() throws Exception {
        // Initialize the database
        customerDataRepository.saveAndFlush(customerData);
        int databaseSizeBeforeDelete = customerDataRepository.findAll().size();

        // Get the customerData
        restCustomerDataMockMvc.perform(delete("/api/customer-data/{id}", customerData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerData> customerDataList = customerDataRepository.findAll();
        assertThat(customerDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerData.class);
    }
}
