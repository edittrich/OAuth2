package de.edittrich.oauth2.proxy.api;

import io.swagger.annotations.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.multipart.MultipartFile;

import de.edittrich.oauth2.proxy.model.AccessToken;
import de.edittrich.oauth2.proxy.model.CodeData;
import de.edittrich.oauth2.proxy.model.ResponseError;

import java.util.List;

import javax.validation.constraints.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-24T15:14:00.334Z")

@Controller
public class RedirectApiController implements RedirectApi {

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

    public ResponseEntity<Void> redirectGET( @NotNull@ApiParam(value = "Authorisation Code", required = true) @RequestParam(value = "code", required = true) String code,
        @ApiParam(value = "State") @RequestParam(value = "state", required = false) String state) {

    	log.debug("Redirect");
    	log.debug("Code: " + code);
    	log.debug("State: " + state);
    	
    	httpSession.setAttribute("proxyCode", code);
    	
    	String url = env.getProperty("proxy.dataURI");
    	url = url + "/codes/"
    			+ code;
    	log.debug("URL: " + url);
    	
        RestTemplate restTemplate = new RestTemplate(); 
        CodeData codeData = new CodeData();
        codeData.setState(state);
        HttpEntity<CodeData> request = new HttpEntity<>(codeData);        
        ResponseEntity<CodeData> response = restTemplate.exchange(url, HttpMethod.POST, request, CodeData.class);        
        log.debug("Response: " + response.getStatusCodeValue());
    	
        url = env.getProperty("proxy.confirmationCodeURI");
        log.debug("URL: " + url);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Location", url);	        
        return new ResponseEntity<Void>(responseHeaders, HttpStatus.TEMPORARY_REDIRECT);
        
    }

}