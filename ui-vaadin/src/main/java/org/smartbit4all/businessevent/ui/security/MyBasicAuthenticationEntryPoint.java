package org.smartbit4all.businessevent.ui.security;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

  @Override
  public void afterPropertiesSet() {
    setRealmName("viszonyaim");
    super.afterPropertiesSet();
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      org.springframework.security.core.AuthenticationException authEx) throws IOException {
    response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    PrintWriter writer = response.getWriter();
    writer.println("HTTP Status 401 - " + authEx.getMessage());
  }

  // @Override
  // public void commence(
  // HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
  // throws IOException {
  // response.addHeader("WWW-Authenticate", "Basic realm="" + getRealmName() + """);
  // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  // PrintWriter writer = response.getWriter();
  // writer.println("HTTP Status 401 - " + authEx.getMessage());
  // }
  //
  // @Override
  // public void afterPropertiesSet() throws Exception {
  // setRealmName("Baeldung");
  // super.afterPropertiesSet();
  // }
}
