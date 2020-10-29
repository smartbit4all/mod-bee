package org.smartbit4all.businessevent.domain.service;

import org.smartbit4all.core.SB4Function;
import org.smartbit4all.types.binarydata.BinaryData;

/**
 * If the event is not a function call then we can use this process event generic function. In this
 * case we have the {@link BusinessEventData} as an input.
 * 
 * @author Peter Boros
 */
public interface ProcessEvent extends SB4Function<BusinessEventData, BinaryData> {

  ProcessEvent event(BusinessEventData event);

}
