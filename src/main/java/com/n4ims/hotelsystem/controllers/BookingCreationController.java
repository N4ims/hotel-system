package com.n4ims.hotelsystem.controllers;

import com.n4ims.hotelsystem.entities.*;
import com.n4ims.hotelsystem.persistence.BookingDataService;
import com.n4ims.hotelsystem.persistence.BookingDataServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import utils.DateUtils;
import utils.DecimalTextFormatter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;

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
    @FXML
    private TextField adultsNumberPicker;
    @FXML
    private TextField childrenNumberPicker;
    @FXML
    private ChoiceBox<CateringTypeEntity> cateringTypePicker;
    @FXML
    private ChoiceBox packageTypePicker;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField birthDayTextField;
    @FXML
    private TextField birthMonthTextField;
    @FXML
    private TextField birthYearTextField;
    @FXML
    private ChoiceBox countryCodePicker;
    @FXML
    private TextField telephoneNumberTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField postcodeTextField;
    @FXML
    private TextField placeTextField;
    @FXML
    private TextField streetNameTextField;
    @FXML
    private TextField streetNumberTextField;


    public BookingCreationController(){
        this.bookingDataService = new BookingDataServiceImpl();
    }

    public void initialize(){

        // Avoid useless reloading of view
        navigationBarController.disableNavigationItem(1);
        adultsNumberPicker.setTextFormatter(new DecimalTextFormatter(0, 100));
        childrenNumberPicker.setTextFormatter(new DecimalTextFormatter(0, 100));
        birthDayTextField.setTextFormatter(new DecimalTextFormatter(1, 31));
        birthMonthTextField.setTextFormatter(new DecimalTextFormatter(1, 12));
        birthYearTextField.setTextFormatter(new DecimalTextFormatter(1900, LocalDate.now().getYear()));
        postcodeTextField.setTextFormatter(new DecimalTextFormatter(0, 999999));


        loadValuesIntoPickers();
        setupDatePickers();


        System.out.println("BookingCreationController initialized");
    }

    private void loadValuesIntoPickers(){

        componentContentLoader.loadFreeRooms(roomNumberPicker, selectedFromDate, selectedToDate);

        componentContentLoader.loadCateringTypes(cateringTypePicker);
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
        componentContentLoader.loadFreeRooms(roomNumberPicker, selectedFromDate, selectedToDate);
    }

    @FXML
    private void handleOnToDatePicked(ActionEvent event){
        DatePicker picker = (DatePicker) event.getSource();
        selectedToDate = picker.getValue();

        // So selected start date < end date
        fromDateMax = selectedToDate;
        componentContentLoader.loadFreeRooms(roomNumberPicker, selectedFromDate, selectedToDate);
    }

    private void handleOnBookingCreationButtonClicked(ActionEvent event){
        Date fromDate = DateUtils.asDate(selectedFromDate);
        Date toDate = DateUtils.asDate(selectedToDate);
        String adultNumberString = adultsNumberPicker.getText();
        String childNumberString = childrenNumberPicker.getText();
        CateringTypeEntity cateringType = cateringTypePicker.getValue();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String telephoneNumber = telephoneNumberTextField.getText();

        String streetName = streetNameTextField.getText();
        String streetNumber = streetNumberTextField.getText();
        String emailAddress = emailTextField.getText();
        String postcode = postcodeTextField.getText();
        String place = placeTextField.getText();

        int adultsNumber;
        int childrenNumber;
        int birthDay;
        int birthMonth;
        int birthYear;

        RoomEntity room = roomNumberPicker.getValue();

        try {
            adultsNumber = Integer.parseInt(adultNumberString);
        }catch (NumberFormatException e){
            // TODO show label below TextField stating that input has to be number
            return;
        }
        try {
            childrenNumber = Integer.parseInt(childNumberString);
        }catch (NumberFormatException e){
            // TODO show label below TextField stating that input has to be number
            return;
        }
        try {
            birthDay = Integer.parseInt(birthDayTextField.getText());
            birthMonth = Integer.parseInt(birthMonthTextField.getText());
            birthYear = Integer.parseInt(birthYearTextField.getText());
        }catch (NumberFormatException e){
            // TODO show label below TextField stating that input has to be number
            return;
        }

        // If input of guest numbers is not valid
        if(!checkNumberOfGuestsValidity(adultsNumber, childrenNumber, room)){
            return;
        }

        // TODO implement country
        AddressEntity address = new AddressEntity(streetName, streetNumber, place, postcode, "Germany");
        Date birthDate = new Date(birthYear, birthMonth, birthDay);

        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        // TODO check if guest already existing and replace dateString
        GuestEntity guest = new GuestEntity(firstName, lastName, birthDate, address, telephoneNumber, "", emailAddress, new Date(timestamp.getTime()));
        RoomBookingEntity roomBooking = new RoomBookingEntity(guest, room, DateUtils.asDate(selectedFromDate), DateUtils.asDate(selectedToDate), adultsNumber, childrenNumber, timestamp, "");

        bookingDataService.createAddress(address);
        bookingDataService.createGuest(guest);
        bookingDataService.createRoomBooking(roomBooking);
    }

    private boolean checkNumberOfGuestsValidity(int adultsNumber, int childrenNumber, RoomEntity room){
        if(adultsNumber < 0 || childrenNumber < 0) {
            // TODO show label below TextField stating that input has to be <= 0
            return false;
        }

        int personNumber = adultsNumber + childrenNumber;
        if(personNumber > room.getType().getMaxPersons()){
            // TODO show hint that too many persons for room
            return false;
        }
        return true;
    }
}
