package org.smartbit4all.businessevent.ui.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.smartbit4all.businessevent.ui.entities.EventChannelActualProcessStatData;
import org.smartbit4all.businessevent.ui.entities.EventChannelStatData;
import org.smartbit4all.businessevent.ui.entities.EventTypeStatData;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("mock")
public class StatDataServiceMock implements StatDataService {

  @Override
  public long getNoOfAllEvents(LocalDateTime startTime, LocalDateTime finishTime) {
    LocalDateTime tempDateTime = LocalDateTime.from(startTime);
    long days = tempDateTime.until(finishTime, ChronoUnit.DAYS);
    tempDateTime = tempDateTime.plusDays(days);

    long hours = tempDateTime.until(finishTime, ChronoUnit.HOURS);
    tempDateTime = tempDateTime.plusHours(hours);

    long minutes = tempDateTime.until(finishTime, ChronoUnit.MINUTES);
    tempDateTime = tempDateTime.plusMinutes(minutes);

    if (minutes == 15) {
      return 10000;
    } else if (hours == 1) {
      return 100000;
    } else if (days == 7) {
      return 7000000;
    }
    return 1000000;
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
    eventChannelStatData1.setAverageProcessTime(random.nextInt(2801) + 200);

    EventChannelStatData eventChannelStatData2 = new EventChannelStatData();
    eventChannelStatData2.setChannelName("FaceKom");
    eventChannelStatData2.setNoOfSuccess(noOfAllEvents / 4);
    eventChannelStatData2.setNoOfFail(noOfAllEvents / 4);
    eventChannelStatData2.setAverageProcessTime(random.nextInt(2801) + 200);

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
    eventTypeStatData1.setNoOfEvents(22);
    EventTypeStatData eventTypeStatData2 = new EventTypeStatData();
    eventTypeStatData2.setEventName("Kettes");
    eventTypeStatData2.setNoOfEvents(10);
    EventTypeStatData eventTypeStatData3 = new EventTypeStatData();
    eventTypeStatData3.setEventName("Hármas");
    eventTypeStatData3.setNoOfEvents(10);
    EventTypeStatData eventTypeStatData4 = new EventTypeStatData();
    eventTypeStatData4.setEventName("Egy");
    eventTypeStatData4.setNoOfEvents(22);
    EventTypeStatData eventTypeStatData5 = new EventTypeStatData();
    eventTypeStatData5.setEventName("Kettő");
    eventTypeStatData5.setNoOfEvents(10);
    EventTypeStatData eventTypeStatData6 = new EventTypeStatData();
    eventTypeStatData6.setEventName("Három");
    eventTypeStatData6.setNoOfEvents(10);
    EventTypeStatData eventTypeStatData7 = new EventTypeStatData();
    eventTypeStatData7.setEventName("Négy");
    eventTypeStatData7.setNoOfEvents(10);
    EventTypeStatData eventTypeStatData8 = new EventTypeStatData();
    eventTypeStatData8.setEventName("Öt");
    eventTypeStatData8.setNoOfEvents(10);
    EventTypeStatData eventTypeStatData9 = new EventTypeStatData();
    eventTypeStatData9.setEventName("Hat");
    eventTypeStatData9.setNoOfEvents(10);

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
