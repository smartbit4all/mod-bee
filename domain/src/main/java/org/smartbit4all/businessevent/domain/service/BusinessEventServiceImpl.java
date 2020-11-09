package org.smartbit4all.businessevent.domain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartbit4all.businessevent.domain.service.BusinessEventChannel.ChannelType;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class BusinessEventServiceImpl implements BusinessEventService, InitializingBean, DisposableBean {

  private static final Logger log = LoggerFactory.getLogger(BusinessEventServiceImpl.class);

  Lock lockSetup = new ReentrantLock();

  @Autowired
  ApplicationContext ctx;

  /**
   * The storage of the channel objects. This objects are the base root point of the whole messaging
   * infrastructure. They must be able to start, shutdown and work gracefully. Therefore they are
   * not managed by the Spring framework directly.
   */
  Map<String, BusinessEventChannel> channels = new HashMap<>();

  /**
   * The channel beans are autowired by the Spring based on the registered
   * {@link BusinessEventChannel} implementing beans from the current context.
   */
  @Autowired
  private List<BusinessEventChannel> channelBeans;

  /**
   * The storage of the channel objects. This objects are the base root point of the whole messaging
   * infrastructure. They must be able to start, shutdown and work gracefully. Therefore they are
   * not managed by the Spring framework directly.
   */
  Map<String, BusinessEventChannelService> channelServices = new HashMap<>();

  /**
   * The channel beans are autowired by the Spring based on the registered
   * {@link BusinessEventChannel} implementing beans from the current context.
   */
  @Autowired
  private List<BusinessEventChannelService> channelServiceBeans;

  /**
   * The scheduled functions in the current configuration. These will be processed and added to the
   * event channels.
   */
  @SuppressWarnings("rawtypes")
  @Autowired(required = false)
  private List<ScheduledFunction> scheduledFunctions;

  @Override
  public BusinessEventChannel channel(String name) {
    BusinessEventChannel channel = channels.get(name);
    return channel;
  }

  /**
   * Initializing the channels based on the autowired {@link #channelBeans}. And collection all the
   * {@link ScheduledFunction} we have and try to add them to the channels.
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    channelBeans.forEach(channel -> {
      channels.put(channel.getChannelCode(), channel);
    });
    channelServiceBeans.forEach(channel -> {
      channelServices.put(channel.getChannelCode(), channel);
      channel.setSelf(channel);
      channel.start();
    });
    if (scheduledFunctions != null && !scheduledFunctions.isEmpty()) {
      for (@SuppressWarnings("rawtypes")
      ScheduledFunction scheduledFunction : scheduledFunctions) {
        BusinessEventChannelService channelService =
            channelServices.get(scheduledFunction.getChannelCode());
        if (channelService == null) {
          log.error("Unable to schedule the " + scheduledFunction.getClass()
              + " function because the " + scheduledFunction.getChannelCode()
              + " doesn't exist in the current configuration.");
        } else if (channelService.getType() == ChannelType.SYNCHRONOUS) {
          log.error("Unable to schedule the " + scheduledFunction.getClass()
              + " function because the " + scheduledFunction.getChannelCode()
              + " is not ASYNCHRONOUS in the current configuration.");
        } else {
          channelService.registerScheduledFunction(scheduledFunction);
        }
      }
    }
  }

  /**
   * We must shutdown all the channels one by one. This must be a graceful shutdown to avoid loosing
   * events. It's registered as a lifecycle function. Must shutdown all the channels before.
   */
  @Override
  public void destroy() throws Exception {
    for (BusinessEventChannelService channel : channelServices.values()) {
      channel.stop();
    }
  }

}
