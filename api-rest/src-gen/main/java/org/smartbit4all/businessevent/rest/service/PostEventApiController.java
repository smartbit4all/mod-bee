package org.smartbit4all.businessevent.rest.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping("${openapi.businessEventModule.base-path:/v1}")
public class PostEventApiController implements PostEventApi {

    private final PostEventApiDelegate delegate;

    public PostEventApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) PostEventApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new PostEventApiDelegate() {});
    }

    @Override
    public PostEventApiDelegate getDelegate() {
        return delegate;
    }

}
