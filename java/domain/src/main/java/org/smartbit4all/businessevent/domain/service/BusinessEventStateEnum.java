package org.smartbit4all.businessevent.domain.service;

public enum BusinessEventStateEnum {

  /**
   * The event is waiting for allocation.
   */
  A,

  /**
   * The event is waiting for the execution but already allocated.
   */
  W,

  /**
   * The event is currently under execution.
   */
  E,

  /**
   * The event is processed successfully.
   */
  S,

  /**
   * The processing of the event is failed and there is no chance to execute it again.
   */
  F

}
