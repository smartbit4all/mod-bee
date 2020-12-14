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

import java.util.concurrent.TimeUnit;
import org.smartbit4all.businessevent.domain.service.BusinessEventChannel.ChannelType;
import org.smartbit4all.core.SB4Function;

/**
 * A function for the a channels to execute scheduling by a given event. If we add a scheduled
 * function as spring bean then it will be registered as an event in the given channel.
 * 
 * @author Peter Boros
 * @param <I>
 * @param <O>
 */
public interface ScheduledFunction<I, O> extends SB4Function<I, O> {

  /**
   * Parameterize the function to execute with fixed delay. After the execution it will be executed
   * again with this delay.
   * 
   * @param amount
   * @param timeUnit
   * @return
   */
  ScheduledFunction<I, O> fixedDelay(long number, TimeUnit timeUnit);

  /**
   * The code of the channel the scheduled function is running in. It must be an
   * {@link ChannelType#ASYNCHRONOUS} channel. If the channel is not existing or not a proper one
   * then the function won't run!
   * 
   * @param channelCode
   * @return
   */
  ScheduledFunction<I, O> channelCode(String channelCode);

  /**
   * @return
   */
  String getChannelCode();

}
