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
package org.smartbit4all.businessevent.config;

import org.smartbit4all.businessevent.domain.service.BusinessEventChannel;
import org.smartbit4all.businessevent.domain.service.BusinessEventChannel.ChannelOperationMode;
import org.smartbit4all.businessevent.domain.service.BusinessEventChannel.ChannelType;
import org.smartbit4all.businessevent.domain.service.BusinessEventChannelImpl;
import org.smartbit4all.businessevent.domain.service.BusinessEventChannelService;
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
  private BusinessEventChannelImpl channelSync;
  private BusinessEventChannelImpl channelAsync;
  private BusinessEventChannelImpl channelSmsHighPrio;

  @Bean
  public BusinessEventChannel chSync() {
    return initChSync();
  }

  @Bean
  public BusinessEventChannelService chSyncService() {
    return initChSync();
  }

  private BusinessEventChannelImpl initChSync() {
    if (channelSync != null) {
      return channelSync;
    }
    channelSync = new BusinessEventChannelImpl();
    // There won't be any executor instantiated inside the channel.
    channelSync.setType(ChannelType.SYNCHRONOUS);
    channelSync.setChannelCode("chSync");
    channelSync
        .setProcessService((BusinessEventProcessService) appContext.getBean("dummyProcess"));
    return channelSync;
  }

  @Bean
  public BusinessEventChannel chAsync() {
    return initAsync();
  }

  @Bean
  public BusinessEventChannelService chAsyncService() {
    return initAsync();
  }

  private BusinessEventChannelImpl initAsync() {
    if (channelAsync != null) {
      return channelAsync;
    }
    channelAsync = new BusinessEventChannelImpl();
    channelAsync.setExecutionThreadLimit(5);
    channelAsync.setChannelCode("chAsync");
    channelAsync.setType(ChannelType.ASYNCHRONOUS);
    channelAsync.setMode(ChannelOperationMode.ALL_NODE_MAX_THROUGHPUT);
    channelAsync
        .setProcessService((BusinessEventProcessService) appContext.getBean("dummyProcess"));
    return channelAsync;
  }

  @Bean
  public BusinessEventChannel smsHighPrio() {
    return initSmsHighPrio();
  }

  @Bean
  public BusinessEventChannelService smsHighPrioService() {
    return initSmsHighPrio();
  }

  private BusinessEventChannelImpl initSmsHighPrio() {
    if (channelSmsHighPrio != null) {
      return channelSmsHighPrio;
    }
    channelSmsHighPrio = new BusinessEventChannelImpl();
    channelSmsHighPrio.setExecutionThreadLimit(15);
    channelSmsHighPrio.setChannelCode("smsHighPrio");
    channelSmsHighPrio.setType(ChannelType.ASYNCHRONOUS);
    channelSmsHighPrio.setMode(ChannelOperationMode.ALL_NODE_MAX_THROUGHPUT);
    channelSmsHighPrio
        .setProcessService((BusinessEventProcessService) appContext.getBean("smsProcess"));
    return channelSmsHighPrio;
  }

  @Bean
  public BusinessEventChannel smsLowPrio() {
    return initSmsLowPrio();
  }

  @Bean
  public BusinessEventChannelService smsLowPrioService() {
    return initSmsLowPrio();
  }

  private BusinessEventChannelImpl initSmsLowPrio() {
    BusinessEventChannelImpl channelSmsLowPrio = new BusinessEventChannelImpl();
    channelSmsLowPrio.setExecutionThreadLimit(2);
    channelSmsLowPrio.setChannelCode("smsLowPrio");
    channelSmsLowPrio.setType(ChannelType.ASYNCHRONOUS);
    channelSmsLowPrio.setMode(ChannelOperationMode.ALL_NODE_MAX_THROUGHPUT);
    channelSmsLowPrio
        .setProcessService((BusinessEventProcessService) appContext.getBean("smsProcess"));
    return channelSmsLowPrio;
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
