package org.smartbit4all.businessevent.ui.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import org.smartbit4all.businessevent.ui.entities.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

// TODO create separate service for prod
@Service
public class LoginServiceMock implements LoginService {

  private ObjectMapper mapper;

  private List<User> users;

  // @Value("${json.path.SystemUsers}")
  // private String jsonPath;

  public LoginServiceMock() {
    initMapper();
  }

  private void initMapper() {
    if (mapper == null) {
      mapper = new ObjectMapper();
      JavaTimeModule javaTimeModule = new JavaTimeModule();
      javaTimeModule.addDeserializer(LocalDateTime.class,
          new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
      mapper.registerModule(javaTimeModule);
    }
  }

  private List<User> findAll() {
    if (users != null && !users.isEmpty()) {
      return users;
    }
    // Mock user list, for now it's here..
    users = new ArrayList<>();
    addUser("user", "password", true);

    if (users == null)
      users = Collections.emptyList();
    return users;
  }

  private void addUser(String user, String password, boolean isAdmin) {
    Collection<GrantedAuthority> roles = new HashSet<>();
    roles.add(new SimpleGrantedAuthority("user"));
    if (isAdmin) {
      roles.add(new SimpleGrantedAuthority("admin"));
    }
    users.add(new User(user, password, roles));
  }

  public User findByUsername(String username) {
    User user = findAll().stream().filter(bean -> Objects.equals(bean.getUsername(), username))
        .findFirst().orElse(null);
    if (user == null) {
      throw new BadCredentialsException("");
    }
    return user;
  }

  @Override
  public User login(User loginData) {
    String username = loginData.getUsername();
    Objects.requireNonNull(username, "A felhasználói név nem lehet üres!");

    return findByUsername(username);
  }

  @Override
  public void logout(User user) {
    // DO NOTHING
  }
}
