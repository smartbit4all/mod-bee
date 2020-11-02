package org.smartbit4all.bee.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * EventTypeStatData
 */

public class EventTypeStatData   {
  @JsonProperty("eventCode")
  private String eventCode;

  @JsonProperty("eventName")
  private String eventName;

  @JsonProperty("noOfEvents")
  private Long noOfEvents;

  public EventTypeStatData eventCode(String eventCode) {
    this.eventCode = eventCode;
    return this;
  }

  /**
   * Get eventCode
   * @return eventCode
  */
  @ApiModelProperty(value = "")


  public String getEventCode() {
    return eventCode;
  }

  public void setEventCode(String eventCode) {
    this.eventCode = eventCode;
  }

  public EventTypeStatData eventName(String eventName) {
    this.eventName = eventName;
    return this;
  }

  /**
   * Get eventName
   * @return eventName
  */
  @ApiModelProperty(value = "")


  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public EventTypeStatData noOfEvents(Long noOfEvents) {
    this.noOfEvents = noOfEvents;
    return this;
  }

  /**
   * Get noOfEvents
   * @return noOfEvents
  */
  @ApiModelProperty(value = "")


  public Long getNoOfEvents() {
    return noOfEvents;
  }

  public void setNoOfEvents(Long noOfEvents) {
    this.noOfEvents = noOfEvents;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventTypeStatData eventTypeStatData = (EventTypeStatData) o;
    return Objects.equals(this.eventCode, eventTypeStatData.eventCode) &&
        Objects.equals(this.eventName, eventTypeStatData.eventName) &&
        Objects.equals(this.noOfEvents, eventTypeStatData.noOfEvents);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eventCode, eventName, noOfEvents);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventTypeStatData {\n");
    
    sb.append("    eventCode: ").append(toIndentedString(eventCode)).append("\n");
    sb.append("    eventName: ").append(toIndentedString(eventName)).append("\n");
    sb.append("    noOfEvents: ").append(toIndentedString(noOfEvents)).append("\n");
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

