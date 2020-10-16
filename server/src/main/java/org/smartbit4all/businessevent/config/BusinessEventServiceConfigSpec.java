package org.smartbit4all.businessevent.config;

import org.smartbit4all.businessevent.domain.service.BusinessEventChannel;
import org.smartbit4all.businessevent.domain.service.BusinessEventChannel.ChannelOperationMode;
import org.smartbit4all.businessevent.domain.service.BusinessEventChannel.ChannelType;
import org.smartbit4all.businessevent.domain.service.BusinessEventChannelImpl;
import org.smartbit4all.businessevent.domain.service.BusinessEventProcessService;
import org.smartbit4all.businessevent.service.DummyProcessEventService;
import org.smartbit4all.businessevent.service.SmsProcessEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BusinessEventServiceConfigSpec {

  @Autowired
  ApplicationContext appContext;

  @Bean
  public BusinessEventChannel chSync() {
    BusinessEventChannel channel = new BusinessEventChannelImpl();
    // There won't be any executor instantiated inside the channel.
    channel.setType(ChannelType.SYNCHRONOUS);
    channel.setChannelCode("chSync");
    channel.setProcessService((BusinessEventProcessService) appContext.getBean("dummyProcess"));
    return channel;
  }

  @Bean
  public BusinessEventChannel chAsync() {
    BusinessEventChannel channel = new BusinessEventChannelImpl();
    channel.setExecutionThreadLimit(5);
    channel.setChannelCode("chAsync");
    channel.setType(ChannelType.ASYNCHRONOUS);
    channel.setMode(ChannelOperationMode.ALL_NODE_MAX_THROUGHPUT);
    channel.setProcessService((BusinessEventProcessService) appContext.getBean("dummyProcess"));
    return channel;
  }

  @Bean
  public BusinessEventChannel smsHighPrio() {
    BusinessEventChannel channel = new BusinessEventChannelImpl();
    channel.setExecutionThreadLimit(15);
    channel.setChannelCode("smsHighPrio");
    channel.setType(ChannelType.ASYNCHRONOUS);
    channel.setMode(ChannelOperationMode.ALL_NODE_MAX_THROUGHPUT);
    channel.setProcessService((BusinessEventProcessService) appContext.getBean("smsProcess"));
    return channel;
  }

  @Bean
  public BusinessEventChannel smsLowPrio() {
    BusinessEventChannel channel = new BusinessEventChannelImpl();
    channel.setExecutionThreadLimit(2);
    channel.setChannelCode("smsLowPrio");
    channel.setType(ChannelType.ASYNCHRONOUS);
    channel.setMode(ChannelOperationMode.ALL_NODE_MAX_THROUGHPUT);
    channel.setProcessService((BusinessEventProcessService) appContext.getBean("smsProcess"));
    return channel;
  }

  @Bean
  public BusinessEventProcessService dummyProcess() {
    return new DummyProcessEventService();
  }

  @Bean
  public BusinessEventProcessService smsProcess() {
    return new SmsProcessEventService();
  }

}
