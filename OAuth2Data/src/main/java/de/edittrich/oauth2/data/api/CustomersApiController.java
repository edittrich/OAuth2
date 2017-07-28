package de.edittrich.oauth2.data.api;

import de.edittrich.oauth2.data.model.Codes;
import de.edittrich.oauth2.data.model.CustomerData;
import de.edittrich.oauth2.data.model.Customers;
import de.edittrich.oauth2.data.model.CustomersRepository;
import de.edittrich.oauth2.data.model.ResponseError;

import io.swagger.annotations.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-28T14:45:09.156Z")

@Controller
public class CustomersApiController implements CustomersApi {

	private final CustomersRepository customersRepository;
	private static final Logger log = LoggerFactory.getLogger(CustomersApiController.class);

	@Autowired
	CustomersApiController(CustomersRepository customersRepository) {
		this.customersRepository = customersRepository;
	}

	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
		CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
		crlf.setIncludeClientInfo(true);
		crlf.setIncludeQueryString(true);
		crlf.setIncludePayload(true);
		return crlf;
	}

    public ResponseEntity<?> customerGET(@ApiParam(value = "CustomerId",required=true ) @PathVariable("customer_id") String customerId) {

		log.debug("customerGET");

		CustomerData customerData = customersRepository
				.findByCustomerId(customerId)
				.map(c -> {
					log.debug("Found");
					CustomerData cd = new CustomerData();
					cd.setConfirmationCode(c.getConfirmationCode());
					cd.setAccessToken(c.getAccessToken());
					cd.setRefreshToken(c.getRefreshToken());
					cd.setLastChanged(c.getLastChanged());					
					return cd;
				}).orElse(null);

		if (customerData == null) {
			return new ResponseEntity<ResponseError>(new ResponseError("400", "Bad Request"), HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<CustomerData>(customerData, HttpStatus.OK);
		}

    }

    public ResponseEntity<Void> customerPOST(@ApiParam(value = "CustomerId",required=true ) @PathVariable("customer_id") String customerId,
        @ApiParam(value = "OAuth2 authorization state"  )  @Valid @RequestBody CustomerData customerData) {
    	
		log.debug("customersPOST");
		
		Customers customers = customersRepository
				.findByCustomerId(customerId)
				.map(c -> {log.debug("Found");
					return c;})
				.orElse(new Customers(customerId));
				
		customers.setConfirmationCode(customerData.getConfirmationCode());
		customers.setAccessToken(customerData.getAccessToken());
		customers.setRefreshToken(customerData.getRefreshToken());
		customers.setLastChanged(customerData.getLastChanged());
		customersRepository.save(customers);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/customer/{customerId}")
				.buildAndExpand(customers.getCustomerId()).toUri();

		return ResponseEntity.created(location).build();
    }

}