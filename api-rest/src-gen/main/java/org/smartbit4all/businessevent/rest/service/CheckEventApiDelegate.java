package org.smartbit4all.businessevent.rest.service;

import org.smartbit4all.businessevent.rest.model.BusinessEventRequest;
import org.smartbit4all.businessevent.rest.model.BusinessEventState;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A delegate to be called by the {@link CheckEventApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */

public interface CheckEventApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /checkEvent : Check a given event and take the result if it has any.
     *
     * @param body The start request for the process (required)
     * @return successful operation (status code 200)
     * @see CheckEventApi#checkEvent
     */
    default ResponseEntity<BusinessEventState> checkEvent(BusinessEventRequest body) throws Exception {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"eventId\" : 0, \"numberOfProcessing\" : 6, \"channel\" : \"channel\", \"sessionId\" : \"sessionId\", \"state\" : \"NEW\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
