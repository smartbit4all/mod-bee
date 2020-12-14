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

import org.smartbit4all.businessevent.domain.entity.ActiveEventDef;
import org.smartbit4all.businessevent.domain.entity.ApplicationRuntimeDef;
import org.smartbit4all.businessevent.domain.entity.EventBinaryContentDef;
import org.smartbit4all.businessevent.domain.entity.EventBodyDef;
import org.smartbit4all.businessevent.domain.entity.EventChannelDef;
import org.smartbit4all.businessevent.domain.entity.EventProcessLogDef;
import org.smartbit4all.domain.meta.EntityConfiguration;
import org.smartbit4all.domain.meta.MetaConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(MetaConfiguration.class)
@Configuration
public class BusinessEventEntityConfig extends EntityConfiguration { 

  @Bean(EventBodyDef.ENTITY_NAME)
  public EventBodyDef eventBodyDef() {
    return createEntityProxy(EventBodyDef.class);
  }

  @Bean(ActiveEventDef.ENTITY_NAME)
  public ActiveEventDef activeEventDef() {
    return createEntityProxy(ActiveEventDef.class);
  }

  @Bean(EventBinaryContentDef.ENTITY_NAME)
  public EventBinaryContentDef eventBinaryContentDef() {
    return createEntityProxy(EventBinaryContentDef.class);
  }

  @Bean(EventProcessLogDef.ENTITY_NAME)
  public EventProcessLogDef eventProcessLogDef() {
    return createEntityProxy(EventProcessLogDef.class);
  }

  @Bean(ApplicationRuntimeDef.ENTITY_NAME)
  public ApplicationRuntimeDef applicationRuntimeDef() {
    return createEntityProxy(ApplicationRuntimeDef.class);
  }

  @Bean(EventChannelDef.ENTITY_NAME)
  public EventChannelDef eventChannelDef() {
    return createEntityProxy(EventChannelDef.class);
  }


}
