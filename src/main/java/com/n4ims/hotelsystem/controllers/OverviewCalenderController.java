package com.n4ims.hotelsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import com.n4ims.hotelsystem.utils.ResourcePaths;
import java.time.LocalDate;

/**

 The OverviewCalenderController class controls the Overview Calender View in the HotelSystem application.
 It initializes and handles the UI elements, such as the date pickers and the create booking button.
 It also sets the minimum and maximum dates that can be selected on the date pickers, and prevents
 unnecessary reloading of the view when switching language or navigating to it from the navigation bar.
 */
public class OverviewCalenderController extends BasicController{

    /**
     * The create booking button.
     */
    @FXML
    private Button createBookingButton;

    /**
     * The date picker for selecting the starting date.
     */
    @FXML
    private DatePicker fromDatePicker;

    /**
     * The minimum date that can be selected on the starting date picker.
     */
    private final LocalDate startMinDate = LocalDate.now();

    /**
     * The maximum date that can be selected on the starting date picker.
     */
    private final LocalDate startMaxDate = LocalDate.MAX;

    /**
     * The date picker for selecting the ending date.
     */
    @FXML
    private DatePicker toDatePicker;

    /**
     * The minimum date that can be selected on the ending date picker.
     */
    private final LocalDate endMinDate = LocalDate.now().plusDays(1);

    /**
     * The maximum date that can be selected on the ending date picker.
     */
    private final LocalDate endMaxDate = LocalDate.MAX;

    /**
     * Initializes the OverviewCalenderController by setting up the UI elements and
     * preventing unnecessary reloading of the view when switching language or navigating to it
     * from the navigation bar.
     */
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

    /**
     * Handles the create booking button being clicked, by navigating to the booking creation view.
     * @param event The mouse click event.
     */
    @FXML
    private void handleOnCreateButtonClicked(MouseEvent event){
        Button button = (Button) event.getSource();
        Scene scene = button.getScene();
        navigate(scene, ResourcePaths.BOOKING_CREATION_VIEW);
    }
}
