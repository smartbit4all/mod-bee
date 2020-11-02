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
 * EventChannelStatData
 */

public class EventChannelStatData   {
  @JsonProperty("channelCode")
  private String channelCode;

  @JsonProperty("channelName")
  private String channelName;

  @JsonProperty("noOfSuccess")
  private Long noOfSuccess = 0l;

  @JsonProperty("noOfFail")
  private Long noOfFail = 0l;

  @JsonProperty("averageProcessTime")
  private Long averageProcessTime;

  public EventChannelStatData channelCode(String channelCode) {
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

  public EventChannelStatData channelName(String channelName) {
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

  public EventChannelStatData noOfSuccess(Long noOfSuccess) {
    this.noOfSuccess = noOfSuccess;
    return this;
  }

  /**
   * Get noOfSuccess
   * @return noOfSuccess
  */
  @ApiModelProperty(value = "")


  public Long getNoOfSuccess() {
    return noOfSuccess;
  }

  public void setNoOfSuccess(Long noOfSuccess) {
    this.noOfSuccess = noOfSuccess;
  }

  public EventChannelStatData noOfFail(Long noOfFail) {
    this.noOfFail = noOfFail;
    return this;
  }

  /**
   * Get noOfFail
   * @return noOfFail
  */
  @ApiModelProperty(value = "")


  public Long getNoOfFail() {
    return noOfFail;
  }

  public void setNoOfFail(Long noOfFail) {
    this.noOfFail = noOfFail;
  }

  public EventChannelStatData averageProcessTime(Long averageProcessTime) {
    this.averageProcessTime = averageProcessTime;
    return this;
  }

  /**
   * Get averageProcessTime
   * @return averageProcessTime
  */
  @ApiModelProperty(value = "")


  public Long getAverageProcessTime() {
    return averageProcessTime;
  }

  public void setAverageProcessTime(Long averageProcessTime) {
    this.averageProcessTime = averageProcessTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventChannelStatData eventChannelStatData = (EventChannelStatData) o;
    return Objects.equals(this.channelCode, eventChannelStatData.channelCode) &&
        Objects.equals(this.channelName, eventChannelStatData.channelName) &&
        Objects.equals(this.noOfSuccess, eventChannelStatData.noOfSuccess) &&
        Objects.equals(this.noOfFail, eventChannelStatData.noOfFail) &&
        Objects.equals(this.averageProcessTime, eventChannelStatData.averageProcessTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(channelCode, channelName, noOfSuccess, noOfFail, averageProcessTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventChannelStatData {\n");
    
    sb.append("    channelCode: ").append(toIndentedString(channelCode)).append("\n");
    sb.append("    channelName: ").append(toIndentedString(channelName)).append("\n");
    sb.append("    noOfSuccess: ").append(toIndentedString(noOfSuccess)).append("\n");
    sb.append("    noOfFail: ").append(toIndentedString(noOfFail)).append("\n");
    sb.append("    averageProcessTime: ").append(toIndentedString(averageProcessTime)).append("\n");
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

