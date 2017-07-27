package de.edittrich.oauth2.proxy.api;

import io.swagger.annotations.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
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

import de.edittrich.oauth2.proxy.model.ResponseError;

import java.util.List;
import java.util.Scanner;

import javax.validation.constraints.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-24T15:14:00.334Z")

@Controller
public class ConfirmationCodesApiController implements ConfirmationCodesApi {

	private static final Logger log = LoggerFactory.getLogger(RedirectApiController.class);
	
    @Autowired
    private Environment env;
    @Autowired
    private HttpSession httpSession;    
    
	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
	    CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
	    crlf.setIncludeClientInfo(true);
	    crlf.setIncludeQueryString(true);
	    crlf.setIncludePayload(true);
	    return crlf;
	}

    public ResponseEntity<?> codePOST( @NotNull@ApiParam(value = "Confirmation Code", required = true) @RequestParam(value = "confirmation_code", required = true) String confirmationCode) {

    	log.debug("ComfirmationCode");

    	String code = (String) httpSession.getAttribute("proxyCode");
    	String state = (String) httpSession.getAttribute("proxyState");
    	
    	if ((code == null) || (state == null)) {
    		return new ResponseEntity<ResponseError>(new ResponseError("400", "Bad Request"), HttpStatus.BAD_REQUEST);
    	}
    	
    	log.debug("Code: " + code);
    	log.debug("State: " + state);
    	log.debug("Confirmation Code: " + confirmationCode);
    	
    	Scanner sc = new Scanner(confirmationCode);
    	String regEx = "[0-9]{4}";
    	if (sc.hasNext(regEx)) {
    		confirmationCode = sc.next(regEx);
    		log.debug("Confirmation Code: " + confirmationCode + " matches");	
    	} else {
    		log.debug("Confirmation Code: " + confirmationCode + " does not match");
    	}
    	sc.close();
    	
        String url = env.getProperty("amazon.redirectURI") + "?"
        		+ "code=" + code        		
        		+ "&state=" + state;
        log.debug("URL: " + url);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Location", url);	        
        return new ResponseEntity<Void>(responseHeaders, HttpStatus.TEMPORARY_REDIRECT);
        
    }

}