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
