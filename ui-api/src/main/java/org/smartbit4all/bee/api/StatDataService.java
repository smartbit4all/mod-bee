package org.smartbit4all.bee.api;

import java.time.LocalDateTime;
import java.util.List;
import org.smartbit4all.bee.api.model.EventChannelActualProcessStatData;
import org.smartbit4all.bee.api.model.EventChannelStatData;

public interface StatDataService {

  Long getNoOfAllEvents(LocalDateTime startTime, LocalDateTime finishTime);

  List<EventChannelStatData> getEventChannelStatDatas(LocalDateTime startTime,
      LocalDateTime finishTime);

  List<EventChannelActualProcessStatData> getEventChannelActualProcessStatDatas(
      LocalDateTime startTime, LocalDateTime finishTime);

}
