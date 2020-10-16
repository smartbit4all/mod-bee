package org.smartbit4all.businessevent.ui.security;

import java.util.Collections;
import org.smartbit4all.businessevent.ui.entities.User;
import org.smartbit4all.businessevent.ui.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class SystemAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private LoginService loginService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String password = (String) authentication.getCredentials();
    User response = loginService
        .login(new User(authentication.getName(), password, null, Collections.emptyList()));
    return new UsernamePasswordAuthenticationToken(response, null, response.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

}
