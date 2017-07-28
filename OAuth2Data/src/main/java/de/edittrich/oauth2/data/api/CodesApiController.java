package de.edittrich.oauth2.data.api;

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

import de.edittrich.oauth2.data.model.CodeData;
import de.edittrich.oauth2.data.model.Codes;
import de.edittrich.oauth2.data.model.CodesRepository;
import de.edittrich.oauth2.data.model.ResponseError;

import java.net.URI;
import java.util.List;

import javax.validation.constraints.*;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-27T15:59:07.701Z")

@Controller
public class CodesApiController implements CodesApi {

	private final CodesRepository codesRepository;
	private static final Logger log = LoggerFactory.getLogger(CodesApiController.class);

	@Autowired
	CodesApiController(CodesRepository codesRepository) {
		this.codesRepository = codesRepository;
	}

	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
		CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
		crlf.setIncludeClientInfo(true);
		crlf.setIncludeQueryString(true);
		crlf.setIncludePayload(true);
		return crlf;
	}
	
    public ResponseEntity<?> codeDELETE(@ApiParam(value = "OAuth2 authorization code",required=true ) @PathVariable("code") String code) {
    	
		log.debug("codeDELETE");

		Boolean deleted = codesRepository
				.findByCode(code)
				.map(c -> {
					log.debug("Found");
					codesRepository.delete(c);
					return true;
				}).orElse(false);

		if (deleted) {
			return new ResponseEntity<CodeData>(HttpStatus.OK);
		} else {
			return new ResponseEntity<ResponseError>(new ResponseError("400", "Bad Request"), HttpStatus.BAD_REQUEST);
		}
		
    }

    public ResponseEntity<?> codeGET(@ApiParam(value = "OAuth2 authorization code",required=true ) @PathVariable("code") String code) {
    	
		log.debug("codeGET");

		CodeData codeData = codesRepository
				.findByCode(code)
				.map(c -> {
					log.debug("Found");
					CodeData cd = new CodeData();
					cd.setState(c.getState());
					cd.setConfirmationCode(c.getConfirmationCode());
					return cd;
				}).orElse(null);

		if (codeData == null) {
			return new ResponseEntity<ResponseError>(new ResponseError("400", "Bad Request"), HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<CodeData>(codeData, HttpStatus.OK);
		}
		
	}

    public ResponseEntity<Void> codePOST(@ApiParam(value = "OAuth2 authorization code",required=true ) @PathVariable("code") String code,
            @ApiParam(value = "OAuth2 authorization code"  )  @Valid @RequestBody CodeData codeData) {

		log.debug("codePOST");
		
		Codes codes = codesRepository
				.findByCode(code)
				.map(c -> {
					log.debug("Found");
					return c;})
				.orElse(new Codes(code));
				
		codes.setConfirmationCode(codeData.getConfirmationCode());
		codes.setState(codeData.getState());					
		codesRepository.save(codes);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/codes/{code}")
				.buildAndExpand(codes.getCode()).toUri();

		return ResponseEntity.created(location).build();
	}

}