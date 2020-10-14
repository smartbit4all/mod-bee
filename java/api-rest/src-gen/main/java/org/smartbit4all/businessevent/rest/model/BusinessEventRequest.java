package org.smartbit4all.businessevent.rest.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BusinessEventRequest
 */

public class BusinessEventRequest   {
  @JsonProperty("channel")
  private String channel;

  @JsonProperty("sessionId")
  private String sessionId;

  @JsonProperty("actionCode")
  private String actionCode;

  @JsonProperty("businessEntity")
  private String businessEntity;

  @JsonProperty("businessEntityRef")
  private String businessEntityRef;

  @JsonProperty("extensionText")
  private String extensionText;

  @JsonProperty("nextProcessTime")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime nextProcessTime;

  public BusinessEventRequest channel(String channel) {
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

  public BusinessEventRequest sessionId(String sessionId) {
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

  public BusinessEventRequest actionCode(String actionCode) {
    this.actionCode = actionCode;
    return this;
  }

  /**
   * The qualified name of the function to execute
   * @return actionCode
  */
  @ApiModelProperty(required = true, value = "The qualified name of the function to execute")
  @NotNull


  public String getActionCode() {
    return actionCode;
  }

  public void setActionCode(String actionCode) {
    this.actionCode = actionCode;
  }

  public BusinessEventRequest businessEntity(String businessEntity) {
    this.businessEntity = businessEntity;
    return this;
  }

  /**
   * The name of the entity the function execution is related with.
   * @return businessEntity
  */
  @ApiModelProperty(value = "The name of the entity the function execution is related with.")


  public String getBusinessEntity() {
    return businessEntity;
  }

  public void setBusinessEntity(String businessEntity) {
    this.businessEntity = businessEntity;
  }

  public BusinessEventRequest businessEntityRef(String businessEntityRef) {
    this.businessEntityRef = businessEntityRef;
    return this;
  }

  /**
   * The identifier of the related entity. Parameter of the function.
   * @return businessEntityRef
  */
  @ApiModelProperty(value = "The identifier of the related entity. Parameter of the function.")


  public String getBusinessEntityRef() {
    return businessEntityRef;
  }

  public void setBusinessEntityRef(String businessEntityRef) {
    this.businessEntityRef = businessEntityRef;
  }

  public BusinessEventRequest extensionText(String extensionText) {
    this.extensionText = extensionText;
    return this;
  }

  /**
   * A free for parameter for function.
   * @return extensionText
  */
  @ApiModelProperty(value = "A free for parameter for function.")


  public String getExtensionText() {
    return extensionText;
  }

  public void setExtensionText(String extensionText) {
    this.extensionText = extensionText;
  }

  public BusinessEventRequest nextProcessTime(OffsetDateTime nextProcessTime) {
    this.nextProcessTime = nextProcessTime;
    return this;
  }

  /**
   * The next process time in case a scheduled channel.
   * @return nextProcessTime
  */
  @ApiModelProperty(value = "The next process time in case a scheduled channel.")

  @Valid

  public OffsetDateTime getNextProcessTime() {
    return nextProcessTime;
  }

  public void setNextProcessTime(OffsetDateTime nextProcessTime) {
    this.nextProcessTime = nextProcessTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BusinessEventRequest businessEventRequest = (BusinessEventRequest) o;
    return Objects.equals(this.channel, businessEventRequest.channel) &&
        Objects.equals(this.sessionId, businessEventRequest.sessionId) &&
        Objects.equals(this.actionCode, businessEventRequest.actionCode) &&
        Objects.equals(this.businessEntity, businessEventRequest.businessEntity) &&
        Objects.equals(this.businessEntityRef, businessEventRequest.businessEntityRef) &&
        Objects.equals(this.extensionText, businessEventRequest.extensionText) &&
        Objects.equals(this.nextProcessTime, businessEventRequest.nextProcessTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(channel, sessionId, actionCode, businessEntity, businessEntityRef, extensionText, nextProcessTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BusinessEventRequest {\n");
    
    sb.append("    channel: ").append(toIndentedString(channel)).append("\n");
    sb.append("    sessionId: ").append(toIndentedString(sessionId)).append("\n");
    sb.append("    actionCode: ").append(toIndentedString(actionCode)).append("\n");
    sb.append("    businessEntity: ").append(toIndentedString(businessEntity)).append("\n");
    sb.append("    businessEntityRef: ").append(toIndentedString(businessEntityRef)).append("\n");
    sb.append("    extensionText: ").append(toIndentedString(extensionText)).append("\n");
    sb.append("    nextProcessTime: ").append(toIndentedString(nextProcessTime)).append("\n");
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

