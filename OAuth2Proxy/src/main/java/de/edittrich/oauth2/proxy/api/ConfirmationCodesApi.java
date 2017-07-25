/**
 * NOTE: This class is auto generated by the swagger code generator program (2.2.3).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package de.edittrich.oauth2.proxy.api;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import de.edittrich.oauth2.proxy.model.ResponseError;

import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-24T15:14:00.334Z")

@Api(value = "ConfirmationCodes", description = "the ConfirmationCodes API")
public interface ConfirmationCodesApi {

    @ApiOperation(value = "", notes = "Confirmation Codes", response = Void.class, tags={ "Security", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 200, message = "Unexpected error", response = ResponseError.class) })
    
    @RequestMapping(value = "/ConfirmationCodes",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Void> codePOST( @NotNull@ApiParam(value = "Confirmation Code", required = true) @RequestParam(value = "confirmation_code", required = true) String confirmationCode);

}
