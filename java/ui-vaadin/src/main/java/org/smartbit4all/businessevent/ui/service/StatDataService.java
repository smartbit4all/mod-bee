package org.smartbit4all.businessevent.ui.service;

import java.time.LocalDateTime;
import java.util.List;
import org.smartbit4all.businessevent.ui.entities.EventChannelActualProcessStatData;
import org.smartbit4all.businessevent.ui.entities.EventChannelStatData;

public interface StatDataService {

  long getNoOfAllEvents(LocalDateTime startTime, LocalDateTime finishTime);

  List<EventChannelStatData> getEventChannelStatDatas(LocalDateTime startTime,
      LocalDateTime finishTime);

  List<EventChannelActualProcessStatData> getEventChannelActualProcessStatDatas(
      LocalDateTime startTime, LocalDateTime finishTime);

}
