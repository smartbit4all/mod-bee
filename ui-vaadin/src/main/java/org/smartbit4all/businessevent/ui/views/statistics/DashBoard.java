package org.smartbit4all.businessevent.ui.views.statistics;

import java.time.LocalDateTime;
import java.util.List;
import org.smartbit4all.bee.api.StatDataService;
import org.smartbit4all.bee.api.model.EventChannelActualProcessStatData;
import org.smartbit4all.bee.api.model.EventChannelStatData;
import org.smartbit4all.bee.api.model.EventTypeStatData;
import org.smartbit4all.ui.vaadin.components.FlexBoxLayout;
import org.smartbit4all.ui.vaadin.util.LumoStyles;
import org.smartbit4all.ui.vaadin.util.UIUtils;
import org.smartbit4all.ui.vaadin.util.css.BoxSizing;
import org.smartbit4all.ui.vaadin.util.css.Display;
import org.smartbit4all.ui.vaadin.view.ViewFrame;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@CssImport("./bee/styles/views/dashboard.css")
public class DashBoard extends ViewFrame {

  private StatDataService statDataService;
  private static final String CLASS_NAME = "dashboard";
  private static final String MAX_WIDTH = "1060px";
  private static final String DIV_WIDTH = "250px";
  private LocalDateTime currentTime = LocalDateTime.now();
  private LocalDateTime startTime = currentTime.minusMinutes(15);
  private int clickedDiv = 0;


  public DashBoard(StatDataService statDataService) {
    this.statDataService = statDataService;
    setViewContent(createContent(startTime));
  }

  private Component createContent(LocalDateTime startTime) {
    Label currentTimeLabel = getCurrentTimeLabel();
    Component currentWorkingLoad = createCurrentWorkingLoadData();
    Component eventChannelStat = createEventChannelStat(startTime);
    Component actualProcessStatData = createActualProcessStatData();

    FlexBoxLayout content = new FlexBoxLayout(currentTimeLabel, currentWorkingLoad,
        eventChannelStat, actualProcessStatData);
    content.setAlignItems(FlexComponent.Alignment.CENTER);
    content.setFlexDirection(FlexDirection.COLUMN);
    return content;
  }

  private Label getCurrentTimeLabel() {
    Label currentTimeLabel = new Label("Utolsó frissítés: ");
    currentTimeLabel
        .add(currentTime.getYear() + "." + String.format("%02d", currentTime.getMonthValue()) + "."
            + String.format("%02d", currentTime.getDayOfMonth()) + " "
            + String.format("%02d", currentTime.getHour()) + ":"
            + String.format("%02d", currentTime.getMinute()));
    currentTimeLabel.setWidth(MAX_WIDTH);
    return currentTimeLabel;
  }

  private Component createCurrentWorkingLoadData() {
    Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
    refreshButton.addClickListener(event -> {
      currentTime = LocalDateTime.now();
      setViewContent(createContent(startTime));
    });
    FlexBoxLayout headerLayout =
        new FlexBoxLayout(createHeader("Aktuális futási terheltség"), refreshButton);
    headerLayout.setClassName(CLASS_NAME + "__headerLayout");
    FlexBoxLayout actualEventsStatData = new FlexBoxLayout(headerLayout, createActualEventsStats());
    actualEventsStatData.setBoxSizing(BoxSizing.BORDER_BOX);
    actualEventsStatData.setDisplay(Display.BLOCK);
    return actualEventsStatData;
  }

  private FlexBoxLayout createHeader(String title) {
    FlexBoxLayout header = new FlexBoxLayout(UIUtils.createH2Label(title));
    header.setAlignItems(FlexComponent.Alignment.START);
    return header;
  }

  private Component createActualEventsStats() {
    FlexBoxLayout actualEventsStats = new FlexBoxLayout();
    for (int i = 0; i < 4; i++) {
      actualEventsStats.add(createActualEventStat(i));
    }
    return actualEventsStats;
  }

  private Component createActualEventStat(int count) {
    Label timeLabel;
    Div noOfEvents = new Div();
    noOfEvents.addClassName(CLASS_NAME + "__actualEventDiv");
    Label dateLabel;
    LocalDateTime startTime;

    switch (count) {
      case 0:
        startTime = currentTime.minusMinutes(15);
        timeLabel = new Label("AZ ELMÚLT 15 PERC");
        noOfEvents.add(new Label(String
            .valueOf(statDataService.getNoOfAllEvents(currentTime.minusMinutes(15), currentTime))));
        dateLabel = new Label(String.format("%02d", startTime.getHour()) + ":"
            + String.format("%02d", startTime.getMinute()) + " - "
            + String.format("%02d", currentTime.getHour()) + ":"
            + String.format("%02d", currentTime.getMinute()));
        break;
      case 1:
        startTime = currentTime.minusHours(1);
        timeLabel = new Label("AZ ELMÚLT 1 ÓRA");
        noOfEvents.add(new Label(String
            .valueOf(statDataService.getNoOfAllEvents(currentTime.minusHours(1), currentTime))));
        dateLabel = new Label(String.format("%02d", startTime.getHour()) + ":"
            + String.format("%02d", startTime.getMinute()) + " - "
            + String.format("%02d", currentTime.getHour()) + ":"
            + String.format("%02d", currentTime.getMinute()));
        break;
      case 2:
        startTime = currentTime.minusDays(1);
        timeLabel = new Label("AZ ELMÚLT 24 ÓRA");
        noOfEvents.add(new Label(String
            .valueOf(statDataService.getNoOfAllEvents(currentTime.minusDays(1), currentTime))));
        dateLabel = new Label(String.format("%02d", startTime.getMonthValue()) + "."
            + String.format("%02d", startTime.getDayOfMonth()) + " "
            + String.format("%02d", startTime.getHour()) + ":"
            + String.format("%02d", startTime.getMinute()) + " - "
            + String.format("%02d", currentTime.getMonthValue()) + "."
            + String.format("%02d", currentTime.getDayOfMonth()) + " "
            + String.format("%02d", currentTime.getHour()) + ":"
            + String.format("%02d", currentTime.getMinute()));
        break;
      default:
        startTime = currentTime.minusDays(7);
        timeLabel = new Label("AZ ELMÚLT HÉTEN");
        noOfEvents.add(new Label(String
            .valueOf(statDataService.getNoOfAllEvents(currentTime.minusDays(7), currentTime))));
        dateLabel = new Label(String.format("%02d", startTime.getMonthValue()) + "."
            + String.format("%02d", startTime.getDayOfMonth()) + " - "
            + String.format("%02d", currentTime.getMonthValue()) + "."
            + String.format("%02d", currentTime.getDayOfMonth()));
        break;

    }
    noOfEvents.add(new Label(" DB"));

    FlexBoxLayout actualEventLayout = new FlexBoxLayout();
    actualEventLayout.addComponentAsFirst(timeLabel);
    actualEventLayout.addComponentAtIndex(1, noOfEvents);
    actualEventLayout.addComponentAtIndex(2, dateLabel);

    UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, actualEventLayout);
    actualEventLayout.setAlignItems(FlexComponent.Alignment.CENTER);
    actualEventLayout.setFlexDirection(FlexDirection.COLUMN);

    Div actualEventStat = new Div(actualEventLayout);
    actualEventStat.setWidth(DIV_WIDTH);
    actualEventStat.addClassName(
        count == clickedDiv ? CLASS_NAME + "__actualEvent_clicked" : CLASS_NAME + "__actualEvent");

    processClickListener(count, actualEventStat);
    return actualEventStat;
  }

  private void processClickListener(int count, Div eventStat) {
    eventStat.addClickListener(click -> {
      switch (count) {
        case 0:
          this.startTime = currentTime.minusMinutes(15);
          break;
        case 1:
          this.startTime = currentTime.minusHours(1);
          break;
        case 2:
          this.startTime = currentTime.minusDays(1);
          break;
        case 3:
          this.startTime = currentTime.minusDays(7);
          break;
      }
      clickedDiv = count;
      this.setViewContent(createContent(this.startTime));
    });
  }


  private Component createEventChannelStat(LocalDateTime startTime) {
    FlexBoxLayout eventChannelStat = new FlexBoxLayout();
    List<EventChannelStatData> eventChannelStatData =
        statDataService.getEventChannelStatDatas(startTime, currentTime);

    VerticalLayout channelLayout = new VerticalLayout();
    channelLayout.add(new Label("CSATORNA"));
    channelLayout.setWidth("300%");

    VerticalLayout successLayout = new VerticalLayout();
    successLayout.add(new Label("SIKERES"));

    VerticalLayout failureLayout = new VerticalLayout();
    failureLayout.add(new Label("SIKERTELEN"));

    VerticalLayout rateLayout = new VerticalLayout();
    rateLayout.add(new Label("ARÁNY"));

    VerticalLayout averageProcessTimeLayout = new VerticalLayout();
    averageProcessTimeLayout.add(new Label("ÁTLAGOS FELDOLGOZÁSI IDŐ"));

    for (EventChannelStatData eventChannel : eventChannelStatData) {
      long noOfSuccess = eventChannel.getNoOfSuccess();
      long noOfFailure = eventChannel.getNoOfFail();
      String channelName = eventChannel.getChannelName();
      long averageProcessTime = eventChannel.getAverageProcessTime();

      Div rate = new Div();
      rate.addClassName(CLASS_NAME + "__eventChannelStatArany");

      channelLayout.add(new Label(channelName));
      successLayout.add(new Label(noOfSuccess + " db"));
      failureLayout.add(new Label(noOfFailure + " db"));
      rate.add(
          new Label(Math.round((double) noOfSuccess * 100 / (noOfSuccess + noOfFailure)) + "%"));
      rate.add(" / ");
      rate.add(
          new Label(Math.round((double) noOfFailure * 100 / (noOfSuccess + noOfFailure)) + "%"));
      rateLayout.add(rate);
      averageProcessTimeLayout.add(new Label(averageProcessTime + " ms"));
    }

    eventChannelStat.add(channelLayout, successLayout, failureLayout, rateLayout,
        averageProcessTimeLayout);
    eventChannelStat.setBoxSizing(BoxSizing.BORDER_BOX);
    eventChannelStat.setWidth(MAX_WIDTH);
    eventChannelStat.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
    eventChannelStat.setClassName(CLASS_NAME + "__eventchannel");
    return eventChannelStat;

  }

  private Component createActualProcessStatData() {
    FlexBoxLayout header = createHeader("Aktuális feldolgozási folyamat");
    List<EventChannelActualProcessStatData> eventChannelActualProcessStatDatas =
        statDataService.getEventChannelActualProcessStatDatas(null, null);

    VerticalLayout channelLayout = new VerticalLayout();
    channelLayout.add(new Label("CSATORNA"));
    channelLayout.setWidth("50%");

    VerticalLayout messageTypeLayout = new VerticalLayout();
    messageTypeLayout.add(new Label("ÜZENETTÍPUS"));

    VerticalLayout noOfMessagesLayout = new VerticalLayout();
    noOfMessagesLayout.add(new Label("ÜZENETEK SZÁMA"));

    Label messageTypeLabel;
    Span eventName;
    Span noOfEvents;

    for (EventChannelActualProcessStatData eventChannelActualProcess : eventChannelActualProcessStatDatas) {
      channelLayout.add(new Label(eventChannelActualProcess.getChannelName()));
      Div messageTypes = new Div();
      messageTypes.setMaxWidth("600px");
      messageTypes.addClassName(CLASS_NAME + "__actualProcessStatData");

      for (int i = 0; i < eventChannelActualProcess.getEventData().size() && i < 3; i++) {
        eventName = new Span(eventChannelActualProcess.getEventData().get(i).getEventName());
        noOfEvents = new Span(
            String.valueOf(eventChannelActualProcess.getEventData().get(i).getNoOfEvents()));
        messageTypeLabel = new Label(eventName.getText() + " (" + noOfEvents.getText() + " db)");
        messageTypes.add(messageTypeLabel);
      }

      if (eventChannelActualProcess.getEventData().size() > 3) {
        Button button =
            new Button(new Label("+" + (eventChannelActualProcess.getEventData().size() - 3)));
        button.addThemeName("tertiary-inline");
        ContextMenu contextMenu = new ContextMenu();
        for (int i = 3; i < eventChannelActualProcess.getEventData().size(); i++) {
          eventName = new Span(eventChannelActualProcess.getEventData().get(i).getEventName());
          noOfEvents = new Span(
              String.valueOf(eventChannelActualProcess.getEventData().get(i).getNoOfEvents()));
          messageTypeLabel = new Label(eventName.getText() + " (" + noOfEvents.getText() + " db)");
          contextMenu.addItem(messageTypeLabel);
        }
        contextMenu.setTarget(button);
        messageTypes.add(button);
      }
      messageTypeLayout.add(messageTypes);
      long noOfMessages = 0;
      for (EventTypeStatData data : eventChannelActualProcess.getEventData()) {
        noOfMessages += data.getNoOfEvents();
      }

      noOfMessagesLayout.add(new Label(noOfMessages + " db"));
    }

    FlexBoxLayout actualProcessStatLayout =
        new FlexBoxLayout(channelLayout, messageTypeLayout, noOfMessagesLayout);
    actualProcessStatLayout.setWidth(MAX_WIDTH);
    actualProcessStatLayout.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
    actualProcessStatLayout.setClassName(CLASS_NAME + "__actualProcessStat");

    FlexBoxLayout finalLayout = new FlexBoxLayout(header, actualProcessStatLayout);
    finalLayout.setBoxSizing(BoxSizing.BORDER_BOX);
    finalLayout.setDisplay(Display.BLOCK);
    return finalLayout;
  }
}
