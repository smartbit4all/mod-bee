package org.smartbit4all.businessevent.domain.service;

import java.util.concurrent.ThreadPoolExecutor;

public interface BusinessEventChannel {


  public static enum ChannelType {

    /**
     * The channel has no executor it processes the events on the thread of the caller.
     */
    SYNCHRONOUS,

    /**
     * The channel has a {@link ThreadPoolExecutor} that is responsible for processing the event on
     * a separate thread maintained by an executor.
     */
    ASYNCHRONOUS,

  }

  /**
   * The channels can work in different well defined operation modes.
   * 
   * @author Peter Boros
   */
  public static enum ChannelOperationMode {

    /**
     * The events are processed on every node in a cluster. There is no guarantee for the processing
     * order of the event. This mode is the most effective in the meaning of processing speed. Can
     * use all the resources we have on every node. If there is no relationship between the events
     * and the technology allows to process the events on every node at the same time then it's the
     * best choice.
     */
    ALL_NODE_MAX_THROUGHPUT,

    /**
     * Same as {@link #ALL_NODE_MAX_THROUGHPUT} but it's allocated into one cluster node.
     */
    ONE_NODE_MAX_THROUGHPUT,

    /**
     * The execution of the event is in the order of arrival. By definition it's served with one
     * node.
     */
    FIFO,

    /**
     * The {@link #FIFO} is applied only for the event from the same session. The events can be
     * parallel executed if they are not from the same session.
     */
    FIFO_SESSIONBASED,

    /**
     * The events are executed only by the scheduled times and in this case more than one event can
     * be executed.
     */
    ALL_NODE_BATCH,

    /**
     * The batch execution is always strictly allocated to one runtime node.
     */
    ONE_NODE_BATCH;

  }

  /**
   * The general state of the given channel.
   * 
   * @author Peter Boros
   */
  public static enum ChannelState {

    /**
     * The channel is in stable stopped state. It doesn't accept any event. The post results an
     * exception.
     */
    INACTIVE,

    /**
     * The channel is in a transient phase, the start is in progress. It could be a very short
     * period but under this the events are hold in a temporary queue and waits for the result of
     * the start. When the channel reach the
     */
    STARTING,

    /**
     * The channel is running and accept the posts but store it only into the database.
     */
    ACTIVE_STOREONLY,

    /**
     * The channel is running and accept the posts and start execution immediately.
     */
    ACTIVE_EXECUTE,

    /**
     * The channel is about to shutdown. It stores the posted events but doesn't start execution at
     * all.
     */
    STOPPING

  }

  /**
   * @see ChannelOperationMode
   * @return
   */
  ChannelOperationMode getMode();

  /**
   * {@link ChannelState}
   * 
   * @return
   */
  ChannelState getState();

  /**
   * This is the user API of the business channel. The post returns a function that must be executed
   * later on. This is a builder that allows to setup the function before calling the execute. The
   * post itself is issue a new event instance based on the {@link BusinessEventData} as input
   * parameter. The event is executed one and only one time except if we call the
   * {@link ProcessEvent#reschedule()} during the execution. If the event execution finished with
   * exception then it's executed again if and only if we called the reschedule! So the exception
   * does't mean necessarily to reschedule the execution and to call reschedule there is no need to
   * throw an exception! The channel could be:
   * <ul>
   * <li>{@link ChannelType#SYNCHRONOUS} - In this case the given event is executed on the current
   * thread. The fact of the execution is saved for the given channel so we can examine the event
   * registry as an audit log. If the event process failed (even after several reschedule) no other
   * server will catch this. It won't be executed by other servers!</li>
   * <li>{@link ChannelType#ASYNCHRONOUS} - In this case the given event is executed on the
   * execution thread. The asynchronous event could be executed on any server depending on the
   * expected process time. If there was a failure (the process is not finished) during the
   * execution then another serve will catch the given event and execute it as soon as possible.
   * </li>
   * </ul>
   * 
   * @return
   */
  PostEvent post();

  String getChannelCode();

  Long getRuntimeId();

  /**
   * Returns the channel type that can be {@link ChannelType#SYNCHRONOUS} or
   * {@link ChannelType#ASYNCHRONOUS}.
   * 
   * @return
   */
  ChannelType getType();

}
