package org.smartbit4all.businessevent.ui.entities;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public class User extends org.springframework.security.core.userdetails.User {

  private String token;
  private String basicAuth;

  public User(String username, String password,
      Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

  public User(String username, String password, String token,
      Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getBasicAuth() {
    return basicAuth;
  }

  public void setBasicAuth(String basicAuth) {
    this.basicAuth = basicAuth;
  }

}
