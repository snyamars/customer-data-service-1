package com.dtvla.services.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.dtvla.services.service.CustomerDataService;
import com.dtvla.services.web.rest.data.TestData;
import com.jayway.jsonpath.JsonPath;

/**
 * REST controller for managing CustomerData.
 */
@RestController
@RequestMapping("/api")
public class CustomerDataResource {

    private final Logger log = LoggerFactory.getLogger(CustomerDataResource.class);

    private static final String ENTITY_NAME = "customerData";
        
    private final CustomerDataService customerDataService;
	
	

    public CustomerDataResource(CustomerDataService customerDataService) {
        this.customerDataService = customerDataService;
    }

	@GetMapping("/customer360")
    @Timed
    public Object customer360(@RequestParam String token, @RequestParam int i_customer, @RequestParam String i_requestId,
    		@RequestParam String i_systemId, @RequestParam String i_country, @RequestParam String format, @RequestParam Boolean i_sync_on) {
        log.info("REST request to get customer360 : {}", token);
		log.info("REST request to get customer360 : {}", i_customer);
		log.info("REST request to get customer360 : {}", i_requestId);
		log.info("REST request to get customer360 : {}", i_systemId);
		log.info("REST request to get customer360 : {}", i_country);
		log.info("REST request to get customer360 : {}", format);
		log.info("REST request to get customer360 : {}", i_sync_on);
		
		//ClassLoader classLoader = getClass().getClassLoader();
		//File file = new File(classLoader.getResource("GetCustomer360ViewDS_Response.json").getFile());
        
		//File file = new File("/data/GetCustomer360ViewDS_Response.json");
		/*Resource resource = new ClassPathResource("data/GetCustomer360ViewDS_Response.json");
		Object obj = null;
    try {
		log.info("Inside try customer360");
        InputStream stream = resource.getInputStream();
        
            obj = JsonPath.read(stream, "$.*");
            
    } catch (IOException e) {        
        log.error("Unable to read the json data file: ",e);
    }*/
		Object obj = JsonPath.read(TestData.customer360, "$.*");
		
        return obj;
    }
    
    @GetMapping("/customerlookup/{key}/{value:.+}")
    @Timed
    public Object customerlookup(@PathVariable String key, @PathVariable String value) {
        
    	log.info("REST request to get customerlookup : {}", key);
		log.info("REST request to get customerlookup : {}", value);
		
		
		
		////ClassLoader classLoader = getClass().getClassLoader();
		//File file = new File(classLoader.getResource("/data/customerlookup_Response.json").getFile());
		
		//File file = new File("/data/customerlookup_Response.json");
		
		String exp ="$..[?(@."+key.toLowerCase()+"=~ /.*"+value+"/i)]";
		Object obj = JsonPath.read(TestData.customerProfile, exp);
		 
		/*try {
			log.info("Inside try customerlookup");
			Resource resource = new ClassPathResource("/customerlookup_Response.json");
			//log.info(resource.getURI().getPath());
			//log.info(resource.getURL().getPath());
			log.info("Inside 1st try customerlookup");
        InputStream stream = resource.getInputStream();
			obj = JsonPath.read(stream, exp);
			log.info("customer profile: "+ obj);
		} catch (IOException e) {
			log.info("Unable to read the json data file: ");
		}
		
		try{
			log.info("Inside 2nd try customerlookup");
			InputStream in = getClass().getResourceAsStream("/customerlookup_Response.json");
			obj = JsonPath.read(in, exp);
			log.info("customer profile: "+ obj);
		} catch (IOException e) {
			log.error("Unable to read the json data file: ",e);
		}*/
        return obj;
    }
	
	@GetMapping("/customerlookup/{customerId}/profile")
    @Timed
    public Object customerlookup(@PathVariable String customerId) {
        
    	log.info("REST request to get customerlookup : {}", customerId);
		
		String exp ="$..[?(@."+"customerId"+"=~ /.*"+customerId+"/i)]";
		Object obj = JsonPath.read(TestData.customerProfile, exp);
		 
		
        return obj;
    }

}
