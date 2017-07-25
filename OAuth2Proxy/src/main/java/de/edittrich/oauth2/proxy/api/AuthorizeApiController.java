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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.validation.constraints.*;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-24T15:14:00.334Z")

@Controller
public class AuthorizeApiController implements AuthorizeApi {

	private static final Logger log = LoggerFactory.getLogger(AuthorizeApiController.class);

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

	public ResponseEntity<Void> authorizeGET(
			@NotNull @ApiParam(value = "Client ID", required = true) @RequestParam(value = "client_id", required = true) String clientId,
			@NotNull @ApiParam(value = "Response Type", required = true, allowableValues = "code", defaultValue = "code") @RequestParam(value = "response_type", required = true, defaultValue = "code") String responseType,
			@NotNull @ApiParam(value = "Redirect URI", required = true) @RequestParam(value = "redirect_uri", required = true) String redirectUri,
			@ApiParam(value = "State") @RequestParam(value = "state", required = false) String state,
			@ApiParam(value = "Scopes list delimited with \"+\"") @RequestParam(value = "scope", required = false) String scope) {

		log.debug("Authorize");
		log.debug("Client Id: " + clientId);
		log.debug("Redirect URI: " + redirectUri);
		log.debug("State: " + state);
		log.debug("Scope: " + scope);

		String url = env.getProperty("proxy.authorizeURI") + "?" + "response_type=" + "code" + "&redirect_uri="
				+ env.getProperty("proxy.redirectURI") + "&client_id=" + env.getProperty("proxy.clientId") + "&state="
				+ state;
		if ((scope != null) && (!scope.equals(""))) {
			try {
				url = url + "&scope=" + URLEncoder.encode(scope, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.err.println("UnsupportedEncodingException: " + e.getMessage());
			}
			
		}
		log.debug("URL: " + url);
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Location", url);
		return new ResponseEntity<Void>(responseHeaders, HttpStatus.TEMPORARY_REDIRECT);

	}

}