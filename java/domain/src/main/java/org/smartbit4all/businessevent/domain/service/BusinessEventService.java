package org.smartbit4all.businessevent.domain.service;

import org.smartbit4all.core.SB4Service;

/**
 * This service provide the functionalities of the business event module. It manages the event
 * channels to access them by name.
 * 
 * @author Peter Boros
 */
public interface BusinessEventService extends SB4Service {

  /**
   * The given channel is retrieved by this function. The behavior could be different based on the
   * implementation. The default implementation always creates the new channel if it doesn't exists.
   * 
   * @param name The symbolic name of the channel. It must be a registered Spring bean.
   * @return If we have a strict configuration for the channels then it can be null!
   */
  BusinessEventChannel channel(String name);

  /**
   * We must shutdown all the channels one by one. This must be a graceful shutdown to avoid loosing
   * events. It's registered as a lifecycle function. Must shutdown all the channels before.
   */
  void onDestroy();

}
