package org.smartbit4all.businessevent.rest.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BusinessEventState
 */

public class BusinessEventState   {
  @JsonProperty("eventId")
  private Long eventId;

  @JsonProperty("channel")
  private String channel;

  @JsonProperty("sessionId")
  private String sessionId;

  @JsonProperty("numberOfProcessing")
  private Long numberOfProcessing;

  /**
   * Gets or Sets state
   */
  public enum StateEnum {
    NEW("NEW"),
    
    PROCESSING("PROCESSING"),
    
    WAITING("WAITING"),
    
    PROCESSED("PROCESSED");

    private String value;

    StateEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StateEnum fromValue(String value) {
      for (StateEnum b : StateEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("state")
  private StateEnum state;

  public BusinessEventState eventId(Long eventId) {
    this.eventId = eventId;
    return this;
  }

  /**
   * Get eventId
   * @return eventId
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getEventId() {
    return eventId;
  }

  public void setEventId(Long eventId) {
    this.eventId = eventId;
  }

  public BusinessEventState channel(String channel) {
    this.channel = channel;
    return this;
  }

  /**
   * Get channel
   * @return channel
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public BusinessEventState sessionId(String sessionId) {
    this.sessionId = sessionId;
    return this;
  }

  /**
   * Get sessionId
   * @return sessionId
  */
  @ApiModelProperty(value = "")


  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public BusinessEventState numberOfProcessing(Long numberOfProcessing) {
    this.numberOfProcessing = numberOfProcessing;
    return this;
  }

  /**
   * Get numberOfProcessing
   * @return numberOfProcessing
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getNumberOfProcessing() {
    return numberOfProcessing;
  }

  public void setNumberOfProcessing(Long numberOfProcessing) {
    this.numberOfProcessing = numberOfProcessing;
  }

  public BusinessEventState state(StateEnum state) {
    this.state = state;
    return this;
  }

  /**
   * Get state
   * @return state
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public StateEnum getState() {
    return state;
  }

  public void setState(StateEnum state) {
    this.state = state;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BusinessEventState businessEventState = (BusinessEventState) o;
    return Objects.equals(this.eventId, businessEventState.eventId) &&
        Objects.equals(this.channel, businessEventState.channel) &&
        Objects.equals(this.sessionId, businessEventState.sessionId) &&
        Objects.equals(this.numberOfProcessing, businessEventState.numberOfProcessing) &&
        Objects.equals(this.state, businessEventState.state);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eventId, channel, sessionId, numberOfProcessing, state);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BusinessEventState {\n");
    
    sb.append("    eventId: ").append(toIndentedString(eventId)).append("\n");
    sb.append("    channel: ").append(toIndentedString(channel)).append("\n");
    sb.append("    sessionId: ").append(toIndentedString(sessionId)).append("\n");
    sb.append("    numberOfProcessing: ").append(toIndentedString(numberOfProcessing)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
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

