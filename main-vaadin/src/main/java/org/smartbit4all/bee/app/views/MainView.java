package org.smartbit4all.bee.app.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartbit4all.ui.vaadin.components.FlexBoxLayout;
import org.smartbit4all.ui.vaadin.components.navigation.bar.AppBar;
import org.smartbit4all.ui.vaadin.components.navigation.bar.TabBar;
import org.smartbit4all.ui.vaadin.components.navigation.drawer.NaviDrawer;
import org.smartbit4all.ui.vaadin.components.navigation.drawer.NaviItem;
import org.smartbit4all.ui.vaadin.components.navigation.drawer.NaviMenu;
import org.smartbit4all.ui.vaadin.util.UIUtils;
import org.smartbit4all.ui.vaadin.util.css.Overflow;
import org.smartbit4all.ui.vaadin.view.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.ErrorHandler;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.Lumo;

@CssImport(value = "./styles/components/charts.css", themeFor = "vaadin-chart",
    include = "vaadin-chart-default-theme")
@CssImport(value = "./styles/components/floating-action-button.css", themeFor = "vaadin-button")
@CssImport(value = "./styles/components/grid.css", themeFor = "vaadin-grid")
@CssImport("./styles/lumo/border-radius.css")
@CssImport("./styles/lumo/icon-size.css")
@CssImport("./styles/lumo/margin.css")
@CssImport("./styles/lumo/padding.css")
@CssImport("./styles/lumo/shadow.css")
@CssImport("./styles/lumo/spacing.css")
@CssImport("./styles/lumo/typography.css")
@CssImport("./styles/misc/box-shadow-borders.css")
@CssImport(value = "./styles/styles.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@Push
@UIScope
public class MainView extends FlexBoxLayout
    implements MainLayout, RouterLayout, PageConfigurator, AfterNavigationObserver {

  private static final Logger log = LoggerFactory.getLogger(MainView.class);
  private static final String CLASS_NAME = "root";

  private Div appHeaderOuter;

  private FlexBoxLayout row;
  private NaviDrawer naviDrawer;
  private FlexBoxLayout column;

  private Div appHeaderInner;
  private FlexBoxLayout viewContainer;
  private Div appFooterInner;

  private Div appFooterOuter;

  private TabBar tabBar;
  private boolean navigationTabs = false;
  private AppBar appBar;

  public MainView() {
    VaadinSession.getCurrent().setErrorHandler((ErrorHandler) errorEvent -> {
      log.error("Uncaught UI exception", errorEvent.getThrowable());
      Notification.show("We are sorry, but an internal error occurred");
    });

    addClassName(CLASS_NAME);
    setFlexDirection(FlexDirection.COLUMN);
    setSizeFull();

    // Initialise the UI building blocks
    initStructure();

    // Populate the navigation drawer
    initNaviItems();

    // Configure the headers and footers (optional)
    initHeadersAndFooters();
  }

  /**
   * Initialise the required components and containers.
   */
  private void initStructure() {
    naviDrawer = new NaviDrawer(getTranslation("login.appname"), "bee-v1.jpg");

    viewContainer = new FlexBoxLayout();
    viewContainer.addClassName(CLASS_NAME + "__view-container");
    viewContainer.setOverflow(Overflow.HIDDEN);

    column = new FlexBoxLayout(viewContainer);
    column.addClassName(CLASS_NAME + "__column");
    column.setFlexDirection(FlexDirection.COLUMN);
    column.setFlexGrow(1, viewContainer);
    column.setOverflow(Overflow.HIDDEN);

    row = new FlexBoxLayout(naviDrawer, column);
    row.addClassName(CLASS_NAME + "__row");
    row.setFlexGrow(1, column);
    row.setOverflow(Overflow.HIDDEN);
    add(row);
    setFlexGrow(1, row);
  }

  /**
   * Initialise the navigation items.
   */
  private void initNaviItems() {
    NaviMenu menu = naviDrawer.getMenu();
    menu.addNaviItem(VaadinIcon.HOME, getTranslation("menu.home"), Home.class);
    // menu.addNaviItem(VaadinIcon.DASHBOARD, getTranslation("menu.dashboard"), DashBoard.class);
    // menu.addNaviItem(VaadinIcon.CHART, getTranslation("menu.statistics"), Statistics.class);
    menu.addNaviItem(VaadinIcon.CHART, getTranslation("menu.statistics"), DashboardFrame.class);
    // menu.addNaviItem(VaadinIcon.DATABASE, getTranslation("menu.saved-statistics"),
    // SavedStatistics.class);

  }

  /**
   * Configure the app's inner and outer headers and footers.
   */
  private void initHeadersAndFooters() {
    // setAppHeaderOuter();
    // setAppFooterInner();
    // setAppFooterOuter();

    // Default inner header setup:
    // - When using tabbed navigation the view title, user avatar and main menu
    // button will appear
    // in the TabBar.
    // - When tabbed navigation is turned off they appear in the AppBar.

    appBar = new AppBar(Home.class, "");

    appBar.getThemeButton().addClickListener(e -> {
      ThemeList themeList = UI.getCurrent().getElement().getThemeList(); //

      if (themeList.contains(Lumo.DARK)) { //
        themeList.remove(Lumo.DARK);
      } else {
        themeList.add(Lumo.DARK);
      }
    });

    // Tabbed navigation
    if (navigationTabs) {
      tabBar = new TabBar(Home.class);
      UIUtils.setTheme(Lumo.DARK, tabBar);

      // Shift-click to add a new tab
      for (NaviItem item : naviDrawer.getMenu().getNaviItems()) {
        item.addClickListener(e -> {
          if (e.getButton() == 0 && e.isShiftKey()) {
            tabBar
                .setSelectedTab(tabBar.addClosableTab(item.getText(), item.getNavigationTarget()));
          }
        });
      }
      appBar.getAvatar().setVisible(false);
      setAppHeaderInner(tabBar, appBar);

      // Default navigation
    } else {
      UIUtils.setTheme(Lumo.DARK, appBar);
      setAppHeaderInner(appBar);
    }
  }

  private void setAppHeaderOuter(Component... components) {
    if (appHeaderOuter == null) {
      appHeaderOuter = new Div();
      appHeaderOuter.addClassName("app-header-outer");
      getElement().insertChild(0, appHeaderOuter.getElement());
    }
    appHeaderOuter.removeAll();
    appHeaderOuter.add(components);
  }

  private void setAppHeaderInner(Component... components) {
    if (appHeaderInner == null) {
      appHeaderInner = new Div();
      appHeaderInner.addClassName("app-header-inner");
      column.getElement().insertChild(0, appHeaderInner.getElement());
    }
    appHeaderInner.removeAll();
    appHeaderInner.add(components);
  }

  private void setAppFooterInner(Component... components) {
    if (appFooterInner == null) {
      appFooterInner = new Div();
      appFooterInner.addClassName("app-footer-inner");
      column.getElement().insertChild(column.getElement().getChildCount(),
          appFooterInner.getElement());
    }
    appFooterInner.removeAll();
    appFooterInner.add(components);
  }

  private void setAppFooterOuter(Component... components) {
    if (appFooterOuter == null) {
      appFooterOuter = new Div();
      appFooterOuter.addClassName("app-footer-outer");
      getElement().insertChild(getElement().getChildCount(), appFooterOuter.getElement());
    }
    appFooterOuter.removeAll();
    appFooterOuter.add(components);
  }

  @Override
  public void configurePage(InitialPageSettings settings) {
    settings.addMetaTag("apple-mobile-web-app-capable", "yes");
    settings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");
  }

  @Override
  public void showRouterLayoutContent(HasElement content) {
    this.viewContainer.getElement().appendChild(content.getElement());
  }

  @Override
  public NaviDrawer getNaviDrawer() {
    return naviDrawer;
  }

  @Override
  public AppBar getAppBar() {
    return appBar;
  }

  @Override
  public void afterNavigation(AfterNavigationEvent event) {
    if (navigationTabs) {
      afterNavigationWithTabs(event);
    } else {
      afterNavigationWithoutTabs(event);
    }
  }

  private void afterNavigationWithTabs(AfterNavigationEvent e) {
    NaviItem active = getActiveItem(e);
    if (active == null) {
      if (tabBar.getTabCount() == 0) {
        tabBar.addClosableTab("", Home.class);
      }
    } else {
      if (tabBar.getTabCount() > 0) {
        tabBar.updateSelectedTab(active.getText(), active.getNavigationTarget());
      } else {
        tabBar.addClosableTab(active.getText(), active.getNavigationTarget());
      }
    }
    appBar.getMenuIcon().setVisible(false);
  }

  private NaviItem getActiveItem(AfterNavigationEvent e) {
    for (NaviItem item : naviDrawer.getMenu().getNaviItems()) {
      if (item.isHighlighted(e)) {
        return item;
      }
    }
    return null;
  }

  private void afterNavigationWithoutTabs(AfterNavigationEvent e) {
    NaviItem active = getActiveItem(e);
    if (active != null) {
      getAppBar().setTitle(active.getText());
    }
  }

  @Override
  public <T extends Component> Class<T> getHomeView() {
    return (Class<T>) Home.class;
  }

}
