package com.n4ims.hotelsystem.controllers;

import com.n4ims.hotelsystem.entities.RoomEntity;
import com.n4ims.hotelsystem.persistence.BookingDataService;
import com.n4ims.hotelsystem.persistence.BookingDataServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import utils.DateUtils;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;

public class BookingCreationController extends BasicController{

    protected BookingDataService bookingDataService;

    @FXML
    private DatePicker fromDatePicker;
    private LocalDate selectedFromDate;
    private final LocalDate fromDateMin = LocalDate.now();
    private LocalDate fromDateMax = LocalDate.MAX;

    @FXML
    private DatePicker toDatePicker;
    private LocalDate selectedToDate;
    private LocalDate toDateMin = LocalDate.now().plusDays(1);
    private final LocalDate toDateMax = LocalDate.MAX;


    @FXML
    private ChoiceBox<RoomEntity> roomNumberPicker;
    ObservableList<RoomEntity> freeRooms;


    public BookingCreationController(){
        this.bookingDataService = new BookingDataServiceImpl();
    }

    public void initialize(){

        // Avoid useless reloading of view
        getNavigationBarController().disableNavigationItem(1);

        setupDatePickers();
        loadValuesIntoComboboxes();

        System.out.println("BookingCreationController initialized");
    }

    private void loadValuesIntoComboboxes(){
        Thread thread = new Thread(){
            public void run() {
                Date fromDate = null;
                Date toDate = null;

                if (selectedFromDate != null) {
                    fromDate = DateUtils.asDate(selectedFromDate);
                }

                if(selectedToDate != null) {
                    toDate = DateUtils.asDate(selectedToDate);
                }

                List<RoomEntity> r = bookingDataService.getAllFreeRoomsForPeriod(fromDate, toDate);
                freeRooms = FXCollections.observableList(r);

                roomNumberPicker.setItems(freeRooms);
            }
        };

        thread.start();
    }

    private void setupDatePickers(){
        // Allow only certain fields of the datePickers to be picked
        Callback<DatePicker, DateCell> startDayCellFactory = super.getDayCellFactory(fromDateMin, fromDateMax);
        fromDatePicker.setDayCellFactory(startDayCellFactory);
        fromDatePicker.setOnAction(this::handleOnFromDatePicked);

        Callback<DatePicker, DateCell> endDayCellFactory = getDayCellFactory(toDateMin, toDateMax);
        toDatePicker.setDayCellFactory(endDayCellFactory);
        toDatePicker.setOnAction(this::handleOnToDatePicked);
    }

    @FXML
    private void handleOnFromDatePicked(ActionEvent event){
        DatePicker picker = (DatePicker) event.getSource();
        selectedFromDate = picker.getValue();

        // So selected start date < end date
        toDateMin = selectedFromDate;
        loadValuesIntoComboboxes();
    }

    @FXML
    private void handleOnToDatePicked(ActionEvent event){
        DatePicker picker = (DatePicker) event.getSource();
        selectedToDate = picker.getValue();

        // So selected start date < end date
        fromDateMax = selectedToDate;
        loadValuesIntoComboboxes();
    }


}
