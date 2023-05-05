package com.n4ims.hotelsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import utils.ResourcePaths;
import java.time.LocalDate;

public class OverviewCalenderController extends BasicController{

    @FXML
    private Button createBookingButton;
    @FXML
    private DatePicker fromDatePicker;
    private final LocalDate startMinDate = LocalDate.now();
    private final LocalDate startMaxDate = LocalDate.MAX;

    @FXML
    private DatePicker toDatePicker;
    private final LocalDate endMinDate = LocalDate.now().plusDays(1);
    private final LocalDate endMaxDate = LocalDate.MAX;


    public void initialize(){
        // enable reloading of this view when switching language
        getImageHeaderController().setSourceViewPath(ResourcePaths.OVERVIEW_CALENDER_VIEW);

        // Avoid useless reloading of view
        getNavigationBarController().disableNavigationItem(0);

        createBookingButton.setOnMouseClicked(this::handleOnCreateButtonClicked);

        // Allow only certain fields of the datePickers to be picked
        Callback<DatePicker, DateCell> startDayCellFactory = getDayCellFactory(startMinDate, startMaxDate);
        fromDatePicker.setDayCellFactory(startDayCellFactory);

        Callback<DatePicker, DateCell> endDayCellFactory = getDayCellFactory(endMinDate, endMaxDate);
        toDatePicker.setDayCellFactory(endDayCellFactory);
    }

    @FXML
    private void handleOnCreateButtonClicked(MouseEvent event){
        Button button = (Button) event.getSource();
        Scene scene = button.getScene();
        System.out.println("Button pressed");
        navigate(scene, ResourcePaths.BOOKING_CREATION_VIEW);
    }
}
