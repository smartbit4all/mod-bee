package org.smartbit4all.bee.api.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.smartbit4all.bee.api.StatDataService;
import org.smartbit4all.bee.api.model.EventChannelActualProcessStatData;
import org.smartbit4all.bee.api.model.EventChannelStatData;
import org.smartbit4all.bee.api.model.EventTypeStatData;
import org.smartbit4all.businessevent.domain.entity.ActiveEventDef;
import org.smartbit4all.businessevent.domain.entity.EventProcessLogDef;
import org.smartbit4all.businessevent.domain.entity.EventProcessLogExtendedDef;
import org.smartbit4all.domain.data.DataRow;
import org.smartbit4all.domain.data.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatDataServiceImpl implements StatDataService {

  @Autowired
  private EventProcessLogDef eventProcessLogDef;

  @Autowired
  private EventProcessLogExtendedDef eventProcessLogExtendedDef;

  @Autowired
  private ActiveEventDef activeEventDef;

  @Override
  public Long getNoOfAllEvents(LocalDateTime startTime, LocalDateTime finishTime) {
    TableData<EventProcessLogDef> data = new TableData<>(eventProcessLogDef);
    try {
      eventProcessLogDef.services().crud().query()
          .select(eventProcessLogDef.count())
          .where(eventProcessLogDef.processFinishTime().ge(startTime).AND(
              eventProcessLogDef.processFinishTime().lt(finishTime)))
          .into(data)
          .execute();
      if (data.rows().size() == 1) {
        return data.rows().get(0).get(eventProcessLogDef.count());
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // TODO Auto-generated method stub
    return 0l;
  }

  @Override
  public List<EventChannelStatData> getEventChannelStatDatas(LocalDateTime startTime,
      LocalDateTime finishTime) {
    TableData<EventProcessLogExtendedDef> data = new TableData<>(eventProcessLogExtendedDef);
    try {
      eventProcessLogExtendedDef.services().crud().query()
          .select(
              eventProcessLogExtendedDef.count(),
              eventProcessLogExtendedDef.eventbody().eventChannel(),
              eventProcessLogExtendedDef.state(),
              eventProcessLogExtendedDef.processTime().avg())
          .where(eventProcessLogExtendedDef.processFinishTime().ge(startTime).AND(
              eventProcessLogExtendedDef.processFinishTime().lt(finishTime)))
          .groupBy(eventProcessLogExtendedDef.eventbody().eventChannel())
          .groupBy(eventProcessLogExtendedDef.state())
          .into(data)
          .execute();
      Map<String, EventChannelStatData> channels = new HashMap<>();
      for (DataRow row : data.rows()) {
        String eventChannel = row.get(eventProcessLogExtendedDef.eventbody().eventChannel());
        EventChannelStatData statData = channels.get(eventChannel);
        if (statData == null) {
          statData = new EventChannelStatData();
          statData.setChannelCode(eventChannel);
          statData.setChannelName(eventChannel);
          channels.put(eventChannel, statData);
        }
        String processState = row.get(eventProcessLogExtendedDef.state());
        Long count = row.get(eventProcessLogExtendedDef.count());
        Long avgProcessTime = row.get(eventProcessLogExtendedDef.processTime().avg());
        if ("S".equals(processState)) {
          statData.setNoOfSuccess(count);
          statData.setAverageProcessTime(avgProcessTime);
        } else if ("F".equals(processState)) {
          statData.setNoOfFail(count);
          statData.setAverageProcessTime(avgProcessTime);
        }
      }
      return new ArrayList<>(channels.values());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return Collections.emptyList();
  }

  @Override
  public List<EventChannelActualProcessStatData> getEventChannelActualProcessStatDatas(
      LocalDateTime startTime, LocalDateTime finishTime) {
    // TODO remove startTime, finishTime
    TableData<ActiveEventDef> data = new TableData<>(activeEventDef);
    try {
      activeEventDef.services().crud().query()
          .select(activeEventDef.count(), activeEventDef.eventChannel(),
              activeEventDef.eventbody().businessEntity())
          .groupBy(activeEventDef.eventChannel())
          .groupBy(activeEventDef.eventbody().businessEntity())
          .into(data)
          .execute();
      Map<String, EventChannelActualProcessStatData> channels = new HashMap<>();
      for (DataRow row : data.rows()) {
        String eventChannel = row.get(activeEventDef.eventChannel());
        EventChannelActualProcessStatData statData = channels.get(eventChannel);
        if (statData == null) {
          statData = new EventChannelActualProcessStatData();
          statData.setChannelCode(eventChannel);
          statData.setChannelName(eventChannel);
          channels.put(eventChannel, statData);
        }
        EventTypeStatData eventTypeStatData = new EventTypeStatData();
        eventTypeStatData.setEventCode(row.get(activeEventDef.eventbody().businessEntity()));
        eventTypeStatData.setEventName(row.get(activeEventDef.eventbody().businessEntity()));
        eventTypeStatData.setNoOfEvents(row.get(activeEventDef.count()));
        statData.getEventData().add(eventTypeStatData);
      }
      return new ArrayList<>(channels.values());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


    return Collections.emptyList();
  }

}
