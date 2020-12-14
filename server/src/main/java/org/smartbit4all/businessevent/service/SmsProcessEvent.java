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
package org.smartbit4all.businessevent.service;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartbit4all.businessevent.domain.service.ProcessEventBase;

public class SmsProcessEvent extends ProcessEventBase {

  private static final Logger log = LoggerFactory.getLogger(SmsProcessEvent.class);

  private Random rnd = new Random();

  @Override
  public void execute() throws Exception {
    // System.out.println("The " + input + " event process completed.");
    int sleepTime = 50 + rnd.nextInt(251);
    Thread.sleep(sleepTime);
  }

}
