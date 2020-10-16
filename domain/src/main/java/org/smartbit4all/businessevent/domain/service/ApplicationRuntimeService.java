package org.smartbit4all.businessevent.domain.service;

import org.smartbit4all.businessevent.domain.entity.ApplicationRuntimeDef;
import org.smartbit4all.core.SB4Service;

/**
 * The application runtime is responsible for managing the current instance of the application. It
 * registers the instance into the database {@link ApplicationRuntimeDef} entity. We can access the
 * unique identifier with the {@link #getAppRuntimeId()} function and the database synchronized time
 * with the {@link #getSystemTime()} function.
 * 
 * @author Peter Boros
 */
public interface ApplicationRuntimeService extends SB4Service {

  void keepAlive() throws Exception;

  /**
   * Be careful it can be null. In this case the application is not registered up till now. So we
   * must wait a little bit for getting the application id.
   * 
   * @return
   */
  Long getAppRuntimeId();

}
