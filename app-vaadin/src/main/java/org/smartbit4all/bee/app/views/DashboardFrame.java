package org.smartbit4all.bee.app.views;

import org.smartbit4all.bee.api.StatDataService;
import org.smartbit4all.businessevent.ui.views.statistics.DashBoard;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("DashBoard")
@Route(value = "", layout = MainLayoutImpl.class)
public class DashboardFrame extends DashBoard {

  public DashboardFrame(StatDataService statDataService) {
    super(statDataService);
  }

}
