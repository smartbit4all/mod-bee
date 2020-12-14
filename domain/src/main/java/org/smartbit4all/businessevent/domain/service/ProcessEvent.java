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

import org.smartbit4all.core.SB4Function;
import org.smartbit4all.types.binarydata.BinaryData;

/**
 * If the event is not a function call then we can use this process event generic function. In this
 * case we have the {@link BusinessEventData} as an input. As a result we can add post actions. The
 * possible post actions are the followings:
 * <ul>
 * <li>Post another event to any other channel. In this case we need to know the channel, construct
 * the post and parameterize it.</li>
 * <li>Schedule retry with the same event.</li>
 * <li>???</li>
 * </ul>
 * 
 * @author Peter Boros
 */
public interface ProcessEvent extends SB4Function<BusinessEventData, BinaryData> {

  /**
   * We can set the event with this builder API.
   * 
   * @param event
   * @return Fluid API.
   */
  ProcessEvent event(BusinessEventData event);

  /**
   * The state of the currently processed event.
   * 
   * @param eventState
   * @return
   */
  ProcessEvent eventState(BusinessEventState eventState);

  /**
   * We can initiate a reschedule post function with this function builder.
   * 
   * @return
   */
  RescheduleEvent reschedule();
  
  boolean rescheduleNeeded();

  /**
   * The channel can be set by this as a builder API.
   * 
   * @param channel The channel the process running in. It's the context for the process.
   * @return
   */
  ProcessEvent channel(BusinessEventChannelService channel);

  /**
   * We can access the
   * 
   * @return
   */
  BusinessEventChannelService getChannel();

  BusinessEventState getEventState();

}
