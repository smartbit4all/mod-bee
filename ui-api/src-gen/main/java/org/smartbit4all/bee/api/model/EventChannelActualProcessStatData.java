package org.smartbit4all.bee.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.smartbit4all.bee.api.model.EventTypeStatData;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * EventChannelActualProcessStatData
 */

public class EventChannelActualProcessStatData   {
  @JsonProperty("channelCode")
  private String channelCode;

  @JsonProperty("channelName")
  private String channelName;

  @JsonProperty("eventData")
  @Valid
  private List<EventTypeStatData> eventData = null;

  public EventChannelActualProcessStatData channelCode(String channelCode) {
    this.channelCode = channelCode;
    return this;
  }

  /**
   * Get channelCode
   * @return channelCode
  */
  @ApiModelProperty(value = "")


  public String getChannelCode() {
    return channelCode;
  }

  public void setChannelCode(String channelCode) {
    this.channelCode = channelCode;
  }

  public EventChannelActualProcessStatData channelName(String channelName) {
    this.channelName = channelName;
    return this;
  }

  /**
   * Get channelName
   * @return channelName
  */
  @ApiModelProperty(value = "")


  public String getChannelName() {
    return channelName;
  }

  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }

  public EventChannelActualProcessStatData eventData(List<EventTypeStatData> eventData) {
    this.eventData = eventData;
    return this;
  }

  public EventChannelActualProcessStatData addEventDataItem(EventTypeStatData eventDataItem) {
    if (this.eventData == null) {
      this.eventData = new ArrayList<>();
    }
    this.eventData.add(eventDataItem);
    return this;
  }

  /**
   * Get eventData
   * @return eventData
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<EventTypeStatData> getEventData() {
    return eventData;
  }

  public void setEventData(List<EventTypeStatData> eventData) {
    this.eventData = eventData;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventChannelActualProcessStatData eventChannelActualProcessStatData = (EventChannelActualProcessStatData) o;
    return Objects.equals(this.channelCode, eventChannelActualProcessStatData.channelCode) &&
        Objects.equals(this.channelName, eventChannelActualProcessStatData.channelName) &&
        Objects.equals(this.eventData, eventChannelActualProcessStatData.eventData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(channelCode, channelName, eventData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventChannelActualProcessStatData {\n");
    
    sb.append("    channelCode: ").append(toIndentedString(channelCode)).append("\n");
    sb.append("    channelName: ").append(toIndentedString(channelName)).append("\n");
    sb.append("    eventData: ").append(toIndentedString(eventData)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

