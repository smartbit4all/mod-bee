/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (4.3.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.smartbit4all.businessevent.rest.service;

import org.smartbit4all.businessevent.rest.model.BusinessEventRequest;
import org.smartbit4all.businessevent.rest.model.BusinessEventState;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@Validated
@Api(value = "checkEvent", description = "the checkEvent API")
public interface CheckEventApi {

    default CheckEventApiDelegate getDelegate() {
        return new CheckEventApiDelegate() {};
    }

    /**
     * POST /checkEvent : Check a given event and take the result if it has any.
     *
     * @param body The start request for the process (required)
     * @return successful operation (status code 200)
     */
    @ApiOperation(value = "Check a given event and take the result if it has any.", nickname = "checkEvent", notes = "", response = BusinessEventState.class, tags={ "businessevent", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = BusinessEventState.class) })
    @RequestMapping(value = "/checkEvent",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<BusinessEventState> checkEvent(@ApiParam(value = "The start request for the process" ,required=true )  @Valid @RequestBody BusinessEventRequest body) throws Exception {
        return getDelegate().checkEvent(body);
    }

}