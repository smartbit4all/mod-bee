package org.smartbit4all.businessevent.domain.service;

import org.smartbit4all.core.SB4Service;

/**
 * This is a generic service to provide the basic function for an event process.
 * 
 * @author Peter Boros
 */
public interface BusinessEventProcessService extends SB4Service {

  /**
   * This function can process a business event.
   * 
   * @return
   */
  ProcessEvent process();

}
