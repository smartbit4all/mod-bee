/*******************************************************************************
 * Copyright (C) 2020 - 2020 it4all Hungary Kft.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.smartbit4all.businessevent.domain.service;

import java.time.LocalDateTime;
import org.smartbit4all.types.binarydata.BinaryData;

/**
 * A new business event.
 * 
 * @author Peter Boros
 */
public class BusinessEventData {
  
  /**
   * Not manadatory!
   * 
   * If createdAt field is empty, the actual time will be added, when the business event is created. 
   */
  public LocalDateTime createdAt;
  
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
