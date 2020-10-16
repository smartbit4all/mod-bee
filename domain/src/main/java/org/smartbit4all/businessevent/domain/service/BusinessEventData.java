package org.smartbit4all.businessevent.domain.service;

import java.time.LocalDateTime;
import org.smartbit4all.core.utility.BinaryData;

/**
 * A new business event.
 * 
 * @author Peter Boros
 */
public class BusinessEventData {

  /**
   * The channel for the execution. A channel is a setup node for the execution.
   */
  public String channel;

  /**
   * This is the qualified name of the function to execute.
   */
  public String actionCode;

  /**
   * The business entity that is the domain entity for the event.
   */
  public String businessEntity;

  /**
   * This is a unique identifier for the
   */
  public String businessEntityRef;

  /**
   * The extension extra info in the event.
   */
  public String extensionText;

  /**
   * The business session
   */
  public String sessionId;

  /**
   * If the event is scheduled to a given time. If it's null or it's in the past then the event will
   * be executed immediately after the post by the declaration of the channel.
   */
  public LocalDateTime nextProcessTime;
  
  /**
   * The request for the Business Event. It can be null.
   */
  public BinaryData request;

  @Override
  public String toString() {
    return "channel=" + channel + ", actionCode=" + actionCode + ", businessEntity="
        + businessEntity + ", businessEntityRef=" + businessEntityRef + ", extensionText="
        + extensionText + ", sessionId=" + sessionId;
  }

}
