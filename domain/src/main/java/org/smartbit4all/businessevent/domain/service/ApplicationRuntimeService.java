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

import org.smartbit4all.businessevent.domain.entity.ApplicationRuntimeDef;
import org.smartbit4all.core.SB4Service;

/**
 * The application runtime is responsible for managing the current instance of the application. It
 * registers the instance into the database {@link ApplicationRuntimeDef} entity. We can access the
 * unique identifier with the {@link #getAppRuntimeId()} function and the database synchronized time
 * with the {@link #getSystemTime()} function.
 * 
 * @author Peter Boros
 */
public interface ApplicationRuntimeService extends SB4Service {

  void keepAlive() throws Exception;

  /**
   * Be careful it can be null. In this case the application is not registered up till now. So we
   * must wait a little bit for getting the application id.
   * 
   * @return
   */
  Long getAppRuntimeId();

}
