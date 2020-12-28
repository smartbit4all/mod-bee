/*******************************************************************************
 * Copyright (C) 2020 - 2020 it4all Hungary Kft.
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.smartbit4all.bee.app.views;

import org.smartbit4all.ui.vaadin.view.ViewFrame;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Welcome")
@Route(value = "home", layout = MainView.class)
public class Home extends ViewFrame {

  public Home() {
    setId("home");
    setViewContent(createContent());
  }

  private Component createContent() {
    Html intro = new Html(
        "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut "
            + "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris "
            + "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit "
            + "esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt "
            + "in culpa qui officia deserunt mollit anim id est laborum.</p>");

    FlexLayout content = new FlexLayout(intro);
    content.setFlexDirection(FlexDirection.COLUMN);
    content.getStyle().set("marging-left", "auto");
    content.getStyle().set("marging-right", "auto");
    content.setMaxWidth("840px");
    content.getStyle().set("padding", "var(--lumo-space-r-l)");
    return content;
  }

}
