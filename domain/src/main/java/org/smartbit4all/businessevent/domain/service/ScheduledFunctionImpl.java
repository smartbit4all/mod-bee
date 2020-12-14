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
import org.smartbit4all.core.SB4FunctionImpl;

public abstract class ScheduledFunctionImpl<I, O> extends SB4FunctionImpl<I, O>
    implements ScheduledFunction<I, O> {

  /**
   * The fixed delay parameter in millisecond. If set then we must execute with fixed delay.
   */
  Long fixedDelay;

  /**
   * The channel code.
   */
  String channelCode;

  /**
   * After successful start we have the channel.
   */
  BusinessEventChannel channel;

  @Override
  public ScheduledFunction<I, O> fixedDelay(long number, TimeUnit timeUnit) {
    fixedDelay = timeUnit.toMillis(number);
    return this;
  }

  @Override
  public ScheduledFunction<I, O> channelCode(String channelCode) {
    this.channelCode = channelCode;
    return this;
  }

  @Override
  public String getChannelCode() {
    return channelCode;
  }

}
