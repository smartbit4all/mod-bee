package org.smartbit4all.businessevent.rest.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping("${openapi.businessEventModule.base-path:/v1}")
public class CheckEventApiController implements CheckEventApi {

    private final CheckEventApiDelegate delegate;

    public CheckEventApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) CheckEventApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new CheckEventApiDelegate() {});
    }

    @Override
    public CheckEventApiDelegate getDelegate() {
        return delegate;
    }

}
