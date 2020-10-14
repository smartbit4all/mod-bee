package org.smartbit4all.businessevent.domain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class BusinessEventServiceImpl implements BusinessEventService {

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
   * Initializing the channelds based on the autowired {@link #channelBeans}.
   */
  @PostConstruct
  public void initChannels() {
    channelBeans.forEach(channel -> {
      channels.put(channel.getChannelCode(), channel);
      channel.setSelf(channel);
      channel.start();
    });
  }



  @Override
  public BusinessEventChannel channel(String name) {
    BusinessEventChannel channel = channels.get(name);
    return channel;
  }

  @Override
  public void onDestroy() {
    for (BusinessEventChannel channel : channels.values()) {
      channel.stop();
    }
  }

}
