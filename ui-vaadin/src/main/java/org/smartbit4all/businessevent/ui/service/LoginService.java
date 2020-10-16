package org.smartbit4all.businessevent.ui.service;

import org.smartbit4all.businessevent.ui.entities.User;

public interface LoginService {

  User login(User user);

  void logout(User user);

}
