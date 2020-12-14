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

import org.smartbit4all.core.SB4FunctionImpl;
import org.smartbit4all.types.binarydata.BinaryData;

/**
 * The basic implementation of the {@link ProcessEvent} interface.
 * 
 * @author Peter Boros
 */
public abstract class ProcessEventBase extends SB4FunctionImpl<BusinessEventData, BinaryData>
    implements ProcessEvent {

  private BusinessEventChannelService channel;

  private BusinessEventState eventState;
  
  private RescheduleEvent rescheduleEvent;

  @Override
  public ProcessEvent event(BusinessEventData event) {
    this.input = event;
    return this;
  }

  @Override
  public RescheduleEvent reschedule() {
    if (rescheduleEvent == null) {
      rescheduleEvent = new RescheduleEventImpl(this);
    }
    return rescheduleEvent;
  }
  
  @Override
  public boolean rescheduleNeeded() {
    return rescheduleEvent != null;
  }

  @Override
  public ProcessEvent channel(BusinessEventChannelService channel) {
    this.channel = channel;
    return this;
  }

  @Override
  public BusinessEventChannelService getChannel() {
    return channel;
  }

  @Override
  public ProcessEvent eventState(BusinessEventState eventState) {
    this.eventState = eventState;
    return this;
  }

  @Override
  public BusinessEventState getEventState() {
    return eventState;
  }

}
