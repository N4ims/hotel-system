package com.n4ims.hotelsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import utils.ResourcePaths;

import java.io.IOException;
import java.time.LocalDate;

public class OverviewCalenderController extends BasicController{

    @FXML
    private ChoiceBox occupationStatusPicker;

    @FXML
    private DatePicker fromDatePicker;
    private final LocalDate startMinDate = LocalDate.now();
    private final LocalDate startMaxDate = LocalDate.MAX;

    @FXML
    private DatePicker toDatePicker;
    private final LocalDate endMinDate = LocalDate.now().plusDays(1);
    private final LocalDate endMaxDate = LocalDate.MAX;


    public void initialize(){
        // Avoid useless reloading of view
        getNavigationBarController().disableNavigationItem(0);

        occupationStatusPicker.setOnMouseClicked(this::handleOnMouseClicked);

        // Allow only certain fields of the datePickers to be picked
        Callback<DatePicker, DateCell> startDayCellFactory = getDayCellFactory(startMinDate, startMaxDate);
        fromDatePicker.setDayCellFactory(startDayCellFactory);

        Callback<DatePicker, DateCell> endDayCellFactory = getDayCellFactory(endMinDate, endMaxDate);
        toDatePicker.setDayCellFactory(endDayCellFactory);

        System.out.println("OverviewController initialized");
    }

    @FXML
    private void handleOnMouseClicked(MouseEvent event){
        if (event.getSource().equals(occupationStatusPicker)) {
            Scene scene = occupationStatusPicker.getScene();

            super.navigate(scene, ResourcePaths.BOOKING_CREATION_VIEW);
        }
    }

}
