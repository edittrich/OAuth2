package de.edittrich.oauth2.data.api;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import de.edittrich.oauth2.data.model.CustomerData;
import de.edittrich.oauth2.data.model.ResponseError;

import java.util.List;

import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-27T14:19:28.420Z")

@Controller
public class CustomersApiController implements CustomersApi {



    public ResponseEntity<Void> customerPOST(@ApiParam(value = "CustomerId",required=true ) @PathVariable("customer_id") String customerId,
        @ApiParam(value = "OAuth2 authorization state"  )  @Valid @RequestBody CustomerData customer_data) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<CustomerData> customrGET(@ApiParam(value = "CustomerId",required=true ) @PathVariable("customer_id") String customerId) {
        // do some magic!
        return new ResponseEntity<CustomerData>(HttpStatus.OK);
    }

}
