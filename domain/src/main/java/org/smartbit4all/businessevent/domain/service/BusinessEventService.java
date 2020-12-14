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

import org.smartbit4all.core.SB4Service;

/**
 * This service provide the functionalities of the business event module. It manages the event
 * channels to access them by name.
 * 
 * @author Peter Boros
 */
public interface BusinessEventService extends SB4Service {

  /**
   * The given channel is retrieved by this function. The behavior could be different based on the
   * implementation. The default implementation always creates the new channel if it doesn't exists.
   * 
   * @param name The symbolic name of the channel. It must be a registered Spring bean.
   * @return If we have a strict configuration for the channels then it can be null!
   */
  BusinessEventChannel channel(String name);

}
