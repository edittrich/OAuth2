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
import de.edittrich.oauth2.proxy.model.ResponseError;

import java.util.Base64;
import java.util.List;

import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-24T15:14:00.334Z")

@Controller
public class TokenApiController implements TokenApi {

	private static final Logger log = LoggerFactory.getLogger(TokenApiController.class);
	
    @Autowired
    private Environment env;
	
	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
	    CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
	    crlf.setIncludeClientInfo(true);
	    crlf.setIncludeQueryString(true);
	    crlf.setIncludePayload(true);
	    return crlf;
	}

    public ResponseEntity<AccessToken> tokenPOST(@ApiParam(value = "Basic Authorization" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,
         @NotNull@ApiParam(value = "Redirect URI", required = true) @RequestParam(value = "redirect_uri", required = true) String redirectUri,
         @NotNull@ApiParam(value = "Type of Grant", required = true, allowableValues = "authorization_code, refresh_token", defaultValue = "authorization_code") @RequestParam(value = "grant_type", required = true, defaultValue="authorization_code") String grantType,
        @ApiParam(value = "Authorisation Code") @RequestParam(value = "code", required = false) String code,
        @ApiParam(value = "Refresh Token") @RequestParam(value = "refresh_token", required = false) String refreshToken) {

       	log.debug("Token");
       	
       	byte[] authorizationBytes = Base64.getDecoder().decode(authorization.substring(6));
       	log.debug("Authorization Header: " + new String(authorizationBytes));
       	       	
        authorization = env.getProperty("proxy.clientId") + ":" + env.getProperty("proxy.clientSecret");        
        byte[] authorizationcoded = Base64.getEncoder().encode(authorization.getBytes());
        String authorizationHeader = "Basic " + new String(authorizationcoded);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Authorization", authorizationHeader);

    	String url = env.getProperty("proxy.tokenURI") + "?" 
    			+ "&grant_type=" + grantType
				+ "&redirect_uri=" + env.getProperty("proxy.redirectURI");
    	if (grantType.equals("authorization_code")) {
    		url = url + "&code=" + code;
    	} else if (grantType.equals("refresh_token")) {
    		url = url + "&refresh_token=" + refreshToken;
    	}
    	
        HttpEntity<String> entity = new HttpEntity<String>("parameters", requestHeaders);        
        RestTemplate restTemplate = new RestTemplate(); 
        ResponseEntity<AccessToken> response = restTemplate.exchange(url, HttpMethod.POST, entity, AccessToken.class);
        log.debug(response.getBody().toString());
        
        return new ResponseEntity<AccessToken>(response.getBody(), response.getStatusCode());    	
    	
    }

}
