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
import de.edittrich.oauth2.proxy.model.CustomerData;
import de.edittrich.oauth2.proxy.model.ResponseError;
import de.edittrich.oauth2.proxy.model.UserInfo;

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

    public ResponseEntity<?> tokenPOST(@ApiParam(value = "Basic Authorization" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,
         @NotNull@ApiParam(value = "Redirect URI", required = true) @RequestParam(value = "redirect_uri", required = true) String redirectUri,
         @NotNull@ApiParam(value = "Type of Grant", required = true, allowableValues = "authorization_code, refresh_token", defaultValue = "authorization_code") @RequestParam(value = "grant_type", required = true, defaultValue="authorization_code") String grantType,
        @ApiParam(value = "Authorisation Code") @RequestParam(value = "code", required = false) String code,
        @ApiParam(value = "Refresh Token") @RequestParam(value = "refresh_token", required = false) String refreshToken) {

       	log.debug("Token");
		log.debug("Authorization: " + authorization);
		log.debug("Redirect URI: " + redirectUri);
		log.debug("Grant Type: " + grantType);
		log.debug("Code: " + code);
		log.debug("Refresh Token: " + refreshToken);
		
		//
		
    	String url = env.getProperty("proxy.tokenURI") + "?" 
    			+ "&grant_type=" + grantType
				+ "&redirect_uri=" + env.getProperty("proxy.redirectURI");
    	if (grantType.equals("authorization_code")) {
        	if ((code == null) || (code.isEmpty())) {
        		return new ResponseEntity<ResponseError>(new ResponseError("400", "Bad Request"), HttpStatus.BAD_REQUEST);
        	} else {
        		url = url + "&code=" + code;
        	}
    	} else if (grantType.equals("refresh_token")) {
        	if ((refreshToken == null) || (refreshToken.isEmpty())) {
        		return new ResponseEntity<ResponseError>(new ResponseError("400", "Bad Request"), HttpStatus.BAD_REQUEST);
        	} else {
        		url = url + "&refresh_token=" + refreshToken;
    		}
    	}
    	log.debug("URL Token: " + url);
    	
       	byte[] authorizationBytesIn = Base64.getDecoder().decode(authorization.substring(6));
       	String authorizationIn = new String(authorizationBytesIn);       	
       	log.debug("Authorization Header In: " + authorizationIn);
       	       	
        String authorizationOut = env.getProperty("proxy.clientId") + ":" + env.getProperty("proxy.clientSecret");        
        byte[] authorizationBytesOut = Base64.getEncoder().encode(authorizationOut.getBytes());
        String authorizationHeaderOut = "Basic " + new String(authorizationBytesOut);
        log.debug("Authorization Header Out: " + authorizationOut);

        RestTemplate restTemplate = new RestTemplate(); 
        
        HttpHeaders requestTokenHeaders = new HttpHeaders();
        requestTokenHeaders.set("Authorization", authorizationHeaderOut);
        HttpEntity<String> entityToken = new HttpEntity<String>("parameters", requestTokenHeaders);        

        ResponseEntity<AccessToken> responseToken = restTemplate.exchange(url, HttpMethod.POST, entityToken, AccessToken.class);
        log.debug("Response Token: " + responseToken.getBody());
        
        //
        
        url = env.getProperty("proxy.userInfoUri");
    	log.debug("URL UserInfo: " + url);
    	
        HttpHeaders requestUserInfoHeaders = new HttpHeaders();
        requestUserInfoHeaders.set("Authorization", "Bearer " + responseToken.getBody().getAccessToken());
        HttpEntity<String> entityUserInfo = new HttpEntity<String>("parameters", requestUserInfoHeaders);        
        ResponseEntity<UserInfo> responseUserInfo = restTemplate.exchange(url, HttpMethod.GET, entityUserInfo, UserInfo.class);
        String customerId = responseUserInfo.getBody().getSub();
        log.debug("UserInfo: " + responseUserInfo.getBody());
        
        //
        
    	url = env.getProperty("proxy.dataURI");
    	url = url + "/codes/"
    			+ code;
    	log.debug("URL Data Codes: " + url);
    	
        ResponseEntity<CodeData> response = restTemplate.exchange(url, HttpMethod.GET, null, CodeData.class);
        String confirmationCode = response.getBody().getConfirmationCode();
        log.debug("Response Data Codes: " + response.getBody());
        
    	url = env.getProperty("proxy.dataURI");
    	url = url + "/customers/"
    			+ customerId;
    	log.debug("URL Data Customers: " + url);
    	
        CustomerData customerData = new CustomerData();
        customerData.setCustomerId(customerId);
        customerData.setConfirmationCode(confirmationCode);
        customerData.setAccessToken(responseToken.getBody().getAccessToken());
        customerData.setRefreshToken(responseToken.getBody().getRefreshToken());
        HttpEntity<CustomerData> requestCustomers = new HttpEntity<>(customerData);        
        ResponseEntity<CustomerData> responseCustomers = restTemplate.exchange(url, HttpMethod.POST, requestCustomers, CustomerData.class);        
        log.debug("Response Data Customers: " + responseCustomers.getStatusCodeValue());
    	
    	//        
        
        return new ResponseEntity<AccessToken>(responseToken.getBody(), responseToken.getStatusCode());    	
    	
    }

}