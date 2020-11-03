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
