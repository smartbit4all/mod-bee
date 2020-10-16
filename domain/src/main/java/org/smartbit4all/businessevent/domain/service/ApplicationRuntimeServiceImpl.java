package org.smartbit4all.businessevent.domain.service;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartbit4all.businessevent.domain.entity.ApplicationRuntimeDef;
import org.smartbit4all.core.utility.concurrent.FutureValue;
import org.smartbit4all.domain.application.TimeManagementService;
import org.smartbit4all.domain.data.TableData;
import org.smartbit4all.domain.data.TableDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

/**
 * The runtime service is a scheduled service that creates and later on updates the
 * {@link ApplicationRuntimeDef} record responsible for the given runtime. This record can be used
 * for the business events to refer as current processor. The events can be allocated again if the
 * runtime is alive any more. The scheduling of this
 * 
 * @author Peter Boros
 */
public class ApplicationRuntimeServiceImpl implements ApplicationRuntimeService {

  private static final Logger log = LoggerFactory.getLogger(ApplicationRuntimeServiceImpl.class);

  /**
   * The unique database identifier of this runtime.
   */
  FutureValue<Long> appRuntimeId = new FutureValue<>();

  @Autowired
  ApplicationRuntimeDef appRuntime;

  @Autowired
  BusinessEventIdService idService;

  @Autowired
  TimeManagementService timeService;

  /**
   * The code of the application running currently.
   */
  @Value("${spring.application.name}")
  String appName;

  @Override
  @Scheduled(fixedRate = 5000)
  @Transactional
  public void keepAlive() throws Exception {
    LocalDateTime now = timeService.getSystemTime();
    if (!appRuntimeId.isDone()) {
      Long id = idService.getNextId();
      TableData<ApplicationRuntimeDef> tableData =
          TableDatas.builder(appRuntime, appRuntime.allProperties()).addRow()
              .set(appRuntime.applicationRuntimeId(), id)
              .set(appRuntime.code(), appName)
              .set(appRuntime.lastTouchTime(), now)
              .set(appRuntime.startTime(), now)
              .set(appRuntime.stopTime(), null).build();
      appRuntime.services().crud().create()
          .values(tableData).execute();
      appRuntimeId.setValue(id);
    } else {
      TableData<ApplicationRuntimeDef> tableData =
          TableDatas.builder(appRuntime, appRuntime.applicationRuntimeId(),
              appRuntime.lastTouchTime()).addRow()
              .set(appRuntime.applicationRuntimeId(), appRuntimeId.get())
              .set(appRuntime.lastTouchTime(), now).build();
      appRuntime.services().crud().update()
          .values(tableData).execute();
    }
  }

  /**
   * Be careful it can be null. In this case the application is not registered up till now. So we
   * must wait a little bit for getting the application id.
   * 
   * @return
   */
  @Override
  public Long getAppRuntimeId() {
    try {
      return appRuntimeId.get();
    } catch (Exception e) {
      log.error("Unable to get the application runtime id. Clustering failed!", e);
    }
    return null;
  }

}
