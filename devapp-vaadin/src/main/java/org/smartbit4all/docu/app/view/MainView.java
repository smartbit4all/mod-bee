package org.smartbit4all.docu.app.view;

import org.smartbit4all.ui.vaadin.util.UIUtils;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

  public MainView() {
    Button button = new Button("Say hello",
        e -> UIUtils.showNotification("Welcome to the BEE DevApp!"));
    button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    button.addClickShortcut(Key.ENTER);

    // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
    addClassName("centered-content");

    add(button);
  }
}
