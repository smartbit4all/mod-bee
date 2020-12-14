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
package org.smartbit4all.businessevent.domain.config;

import org.smartbit4all.businessevent.domain.service.ApplicationRuntimeService;
import org.smartbit4all.businessevent.domain.service.ApplicationRuntimeServiceImpl;
import org.smartbit4all.businessevent.domain.service.BusinessEventIdService;
import org.smartbit4all.businessevent.domain.service.BusinessEventService;
import org.smartbit4all.businessevent.domain.service.BusinessEventServiceImpl;
import org.smartbit4all.core.SB4Configuration;
import org.smartbit4all.domain.meta.MetaConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Import(MetaConfiguration.class)
@Configuration
@EnableScheduling
public class BusinessEventServiceConfig extends SB4Configuration implements SchedulingConfigurer {

  @Bean
  public BusinessEventService businessEventService() {
    return new BusinessEventServiceImpl();
  }

  @Bean
  public BusinessEventIdService businessEventIdService() {
    return new BusinessEventIdService();
  }

  @Bean
  public ApplicationRuntimeService runtimeService() {
    return new ApplicationRuntimeServiceImpl();
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.setTaskScheduler(scheduler());
  }

  @Bean
  public ThreadPoolTaskScheduler scheduler() {
    ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    taskScheduler.setPoolSize(3);
    taskScheduler.setThreadNamePrefix("bee-maintain-thread-");
    return taskScheduler;
  }

  // @Bean
  // @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  // public BusinessEventChannel channelService() {
  // return new BusinessEventChannel();
  // }

}
