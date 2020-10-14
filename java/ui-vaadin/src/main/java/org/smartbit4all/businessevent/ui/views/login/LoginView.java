package org.smartbit4all.businessevent.ui.views.login;

import org.smartbit4all.businessevent.ui.security.SecurityUtils;
import org.smartbit4all.businessevent.ui.views.statistics.DashBoard;
import org.smartbit4all.businessevent.ui.views.statistics.Statistics;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginI18n.ErrorMessage;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Bejelentkezés | NB.EU")
public class LoginView extends VerticalLayout
    implements AfterNavigationObserver, BeforeEnterObserver {

  private LoginOverlay login = new LoginOverlay();

  public LoginView() {
    addClassName("login-view");
    setSizeFull();
    setAlignItems(Alignment.CENTER);
    setJustifyContentMode(JustifyContentMode.CENTER);

    LoginI18n i18n = LoginI18n.createDefault();

    // classic header
    LoginI18n.Header header = new LoginI18n.Header();
    header.setTitle(getTranslation("login.appname"));
    header.setDescription("");
    i18n.setHeader(header);

    // header with icon
    // i18n.setHeader(new LoginI18n.Header());
    // Icon icon = VaadinIcon.DATABASE.create();
    // icon.setSize("64px");
    // icon.getStyle().set("top", "-4px");
    // H1 title = new H1(icon, new Text(" " + getTranslation("login.appname")));
    // title.getStyle().set("color", "var(--lumo-base-color)");
    // login.setTitle(title);

    i18n.setAdditionalInformation(getTranslation("login.additionalinfo"));
    i18n.setForm(new LoginI18n.Form());
    i18n.getForm().setSubmit(getTranslation("login.submit"));
    i18n.getForm().setTitle(getTranslation("login.title"));
    i18n.getForm().setUsername(getTranslation("login.username"));
    i18n.getForm().setPassword(getTranslation("login.password"));
    i18n.getForm().setForgotPassword(getTranslation("login.forgotpassword"));
    ErrorMessage errorMessage = new ErrorMessage();
    errorMessage.setTitle(getTranslation("login.error.title"));
    errorMessage.setTitle(getTranslation("login.error.message"));
    i18n.setErrorMessage(errorMessage);
    login.setI18n(i18n);
    login.setAction("login");
    login.setForgotPasswordButtonVisible(false);

    add(login);
  }

  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    if (SecurityUtils.isUserLoggedIn()) {
      // TODO replace with DashBoard
      //event.forwardTo(Statistics.class);
      event.forwardTo(DashBoard.class);
    } else {
      login.setOpened(true);
    }
  }

  @Override
  public void afterNavigation(AfterNavigationEvent event) {
    login.setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
  }

}
