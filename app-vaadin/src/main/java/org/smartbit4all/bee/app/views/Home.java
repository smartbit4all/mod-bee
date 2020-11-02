package org.smartbit4all.bee.app.views;

import org.smartbit4all.ui.vaadin.components.FlexBoxLayout;
import org.smartbit4all.ui.vaadin.layout.size.Horizontal;
import org.smartbit4all.ui.vaadin.layout.size.Uniform;
import org.smartbit4all.ui.vaadin.view.ViewFrame;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Welcome")
@Route(value = "home", layout = MainLayoutImpl.class)
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

		FlexBoxLayout content = new FlexBoxLayout(intro);
		content.setFlexDirection(FlexDirection.COLUMN);
		content.setMargin(Horizontal.AUTO);
		content.setMaxWidth("840px");
		content.setPadding(Uniform.RESPONSIVE_L);
		return content;
	}

}
