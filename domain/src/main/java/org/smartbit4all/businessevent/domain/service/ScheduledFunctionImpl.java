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
