package org.smartbit4all.businessevent.ui.entities;

public class EventChannelStatData {

  private String channelCode;
  private String channelName;
  private long noOfSuccess = 0;
  private long noOfFail = 0;

  // INNENTŐL
  private long averageProcessTime;
  // IDÁIG

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

  public long getNoOfSuccess() {
    return noOfSuccess;
  }

  public void setNoOfSuccess(long noOfSuccess) {
    this.noOfSuccess = noOfSuccess;
  }

  public long getNoOfFail() {
    return noOfFail;
  }

  public void setNoOfFail(long noOfFail) {
    this.noOfFail = noOfFail;
  }

  // INNENTŐL
  public void setAverageProcessTime(long averageProcessTime) {
    this.averageProcessTime = averageProcessTime;
  }

  public long getAverageProcessTime() {
    return averageProcessTime;
  }
  // IDÁIG
}
