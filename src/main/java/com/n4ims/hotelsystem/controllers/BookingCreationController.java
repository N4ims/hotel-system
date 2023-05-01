package com.n4ims.hotelsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import utils.ResourcePaths;

import java.time.LocalDate;

public class BookingCreationController extends BasicController{

    @FXML
    private DatePicker startDatePicker;
    private LocalDate selectedStartDate;
    private final LocalDate startMinDate = LocalDate.now();
    private LocalDate startMaxDate = LocalDate.MAX;

    @FXML
    private DatePicker endDatePicker;
    private LocalDate selectedEndDate;
    private LocalDate endMinDate = LocalDate.now().plusDays(1);
    private final LocalDate endMaxDate = LocalDate.MAX;

    public void initialize(){
        // Avoid useless reloading of view
        getNavigationBarController().disableNavigationItem(1);

        setupDatePickers();
        loadValuesIntoComboboxes();

        System.out.println("OverviewController initialized");
    }

    private void loadValuesIntoComboboxes(){

    }

    private void setupDatePickers(){
        // Allow only certain fields of the datePickers to be picked
        Callback<DatePicker, DateCell> startDayCellFactory = super.getDayCellFactory(startMinDate, startMaxDate);
        startDatePicker.setDayCellFactory(startDayCellFactory);
        endDatePicker.setOnAction(this::handleOnStartDatePicked);

        Callback<DatePicker, DateCell> endDayCellFactory = getDayCellFactory(endMinDate, endMaxDate);
        endDatePicker.setDayCellFactory(endDayCellFactory);
        endDatePicker.setOnAction(this::handleOnEndDatePicked);
    }

    @FXML
    private void handleOnStartDatePicked(ActionEvent event){
        DatePicker picker = (DatePicker) event.getSource();
        selectedStartDate = picker.getValue();

        // So selected start date < end date
        endMinDate = selectedStartDate;
    }

    @FXML
    private void handleOnEndDatePicked(ActionEvent event){
        DatePicker picker = (DatePicker) event.getSource();
        selectedEndDate = picker.getValue();
        System.out.println("End Picker picked");
        // So selected start date < end date
        startMaxDate = selectedEndDate;
    }
}
