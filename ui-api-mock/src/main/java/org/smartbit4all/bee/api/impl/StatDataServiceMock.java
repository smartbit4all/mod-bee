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
package org.smartbit4all.bee.api.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.smartbit4all.bee.api.StatDataService;
import org.smartbit4all.bee.api.model.EventChannelActualProcessStatData;
import org.smartbit4all.bee.api.model.EventChannelStatData;
import org.smartbit4all.bee.api.model.EventTypeStatData;
import org.springframework.stereotype.Service;

@Service
// @Profile("mock")
public class StatDataServiceMock implements StatDataService {

  @Override
  public Long getNoOfAllEvents(LocalDateTime startTime, LocalDateTime finishTime) {
    LocalDateTime tempDateTime = LocalDateTime.from(startTime);
    long days = tempDateTime.until(finishTime, ChronoUnit.DAYS);
    tempDateTime = tempDateTime.plusDays(days);

    long hours = tempDateTime.until(finishTime, ChronoUnit.HOURS);
    tempDateTime = tempDateTime.plusHours(hours);

    long minutes = tempDateTime.until(finishTime, ChronoUnit.MINUTES);
    tempDateTime = tempDateTime.plusMinutes(minutes);

    if (minutes == 15) {
      return 10000l;
    } else if (hours == 1) {
      return 100000l;
    } else if (days == 7) {
      return 7000000l;
    }
    return 1000000l;
  }

  @Override
  public List<EventChannelStatData> getEventChannelStatDatas(LocalDateTime startTime,
      LocalDateTime finishTime) {
    Random random = new Random();
    long noOfAllEvents =
        getNoOfAllEvents(LocalDateTime.from(startTime), LocalDateTime.from(finishTime));
    EventChannelStatData eventChannelStatData1 = new EventChannelStatData();
    eventChannelStatData1.setChannelName("BFO_UAT");
    eventChannelStatData1.setNoOfSuccess(noOfAllEvents / 4);
    eventChannelStatData1.setNoOfFail(noOfAllEvents / 4);
    int rand1 = random.nextInt(2801) + 200;
    eventChannelStatData1.setAverageProcessTime(Long.valueOf(rand1));

    EventChannelStatData eventChannelStatData2 = new EventChannelStatData();
    eventChannelStatData2.setChannelName("FaceKom");
    eventChannelStatData2.setNoOfSuccess(noOfAllEvents / 4);
    eventChannelStatData2.setNoOfFail(noOfAllEvents / 4);
    int rand2 = random.nextInt(2801) + 200;
    eventChannelStatData2.setAverageProcessTime(Long.valueOf(rand2));

    List<EventChannelStatData> eventChannelStatDataList = new ArrayList<>();
    eventChannelStatDataList.add(eventChannelStatData1);
    eventChannelStatDataList.add(eventChannelStatData2);

    return eventChannelStatDataList;
  }

  @Override
  public List<EventChannelActualProcessStatData> getEventChannelActualProcessStatDatas(
      LocalDateTime startTime, LocalDateTime finishTime) {
    EventTypeStatData eventTypeStatData1 = new EventTypeStatData();
    eventTypeStatData1.setEventName("Ez egy típus");
    eventTypeStatData1.setNoOfEvents(22l);
    EventTypeStatData eventTypeStatData2 = new EventTypeStatData();
    eventTypeStatData2.setEventName("Kettes");
    eventTypeStatData2.setNoOfEvents(10l);
    EventTypeStatData eventTypeStatData3 = new EventTypeStatData();
    eventTypeStatData3.setEventName("Hármas");
    eventTypeStatData3.setNoOfEvents(10l);
    EventTypeStatData eventTypeStatData4 = new EventTypeStatData();
    eventTypeStatData4.setEventName("Egy");
    eventTypeStatData4.setNoOfEvents(22l);
    EventTypeStatData eventTypeStatData5 = new EventTypeStatData();
    eventTypeStatData5.setEventName("Kettő");
    eventTypeStatData5.setNoOfEvents(10l);
    EventTypeStatData eventTypeStatData6 = new EventTypeStatData();
    eventTypeStatData6.setEventName("Három");
    eventTypeStatData6.setNoOfEvents(10l);
    EventTypeStatData eventTypeStatData7 = new EventTypeStatData();
    eventTypeStatData7.setEventName("Négy");
    eventTypeStatData7.setNoOfEvents(10l);
    EventTypeStatData eventTypeStatData8 = new EventTypeStatData();
    eventTypeStatData8.setEventName("Öt");
    eventTypeStatData8.setNoOfEvents(10l);
    EventTypeStatData eventTypeStatData9 = new EventTypeStatData();
    eventTypeStatData9.setEventName("Hat");
    eventTypeStatData9.setNoOfEvents(10l);

    List eventTypeForBFO = new ArrayList();
    eventTypeForBFO.add(eventTypeStatData1);
    eventTypeForBFO.add(eventTypeStatData2);
    eventTypeForBFO.add(eventTypeStatData3);

    List eventTypeForFace = new ArrayList();
    eventTypeForFace.add(eventTypeStatData4);
    eventTypeForFace.add(eventTypeStatData5);
    eventTypeForFace.add(eventTypeStatData6);
    eventTypeForFace.add(eventTypeStatData7);
    eventTypeForFace.add(eventTypeStatData8);
    eventTypeForFace.add(eventTypeStatData9);

    EventChannelActualProcessStatData eventChannelActualProcessStatData1 =
        new EventChannelActualProcessStatData();
    eventChannelActualProcessStatData1.setChannelName("BFO_UAT");
    eventChannelActualProcessStatData1.setEventData(eventTypeForBFO);

    EventChannelActualProcessStatData eventChannelActualProcessStatData2 =
        new EventChannelActualProcessStatData();
    eventChannelActualProcessStatData2.setChannelName("FaceKom");
    eventChannelActualProcessStatData2.setEventData(eventTypeForFace);

    List<EventChannelActualProcessStatData> eventChannelActualProcessStatDataList =
        new ArrayList<>();
    eventChannelActualProcessStatDataList.add(eventChannelActualProcessStatData1);
    eventChannelActualProcessStatDataList.add(eventChannelActualProcessStatData2);

    return eventChannelActualProcessStatDataList;
  }

}
