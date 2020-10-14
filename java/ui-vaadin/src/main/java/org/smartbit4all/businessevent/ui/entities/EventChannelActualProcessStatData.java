package org.smartbit4all.businessevent.ui.entities;

import java.util.ArrayList;
import java.util.List;

public class EventChannelActualProcessStatData {

  private String channelCode;
  private String channelName;
  private List<EventTypeStatData> eventData = new ArrayList<>();

  public String getChannelCode() {
    return channelCode;
  }

  public void setChannelCode(String channelCode) {
    this.channelCode = channelCode;
  }

  public String getChannelName() {
    return channelName;
  }

  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }

  public List<EventTypeStatData> getEventData() {
    return eventData;
  }

  public void setEventData(List<EventTypeStatData> eventData) {
    this.eventData = eventData;
  }

  // TODO when converting to IF this should be a default method
  public long getNoOfAllEvents() {
    long result = 0;
    for (EventTypeStatData data : getEventData()) {
      result += data.getNoOfEvents();
    }
    return result;
  }

}
