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

/**
 * The post is main function of the business event module. We can add a new event to a channel to
 * save it and start processing by the configuration of the channel.
 * 
 * If the channel is configured as synchronous then the processing itself will be executed using the
 * current thread. But the event is saved using an alternative transaction in the meantime.
 * 
 * TODO Implement
 * 
 * If we would like to wait for the result of the process then we can add a call back interface to
 * call after the successful execution.
 * 
 * @author Peter Boros
 */
public interface PostEvent extends SB4Function<BusinessEventData, BusinessEventState> {

  PostEvent event(BusinessEventData event);

  PostEvent function(SB4Function<?, ?> function);

}
