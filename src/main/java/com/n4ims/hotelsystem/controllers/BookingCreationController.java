package com.n4ims.hotelsystem.controllers;

import com.n4ims.hotelsystem.controllers.converters.CateringTypeEntityConverter;
import com.n4ims.hotelsystem.controllers.converters.RoomEntityConverter;
import com.n4ims.hotelsystem.controllers.converters.RoomTypeConverter;
import com.n4ims.hotelsystem.entities.*;
import com.n4ims.hotelsystem.persistence.BookingDataService;
import com.n4ims.hotelsystem.persistence.BookingDataServiceImpl;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import utils.DateUtils;
import utils.DecimalTextFormatter;
import javafx.scene.control.Button;
import utils.ResourcePaths;

import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class BookingCreationController extends BasicController{

    ResourceBundle langBundle;

    protected BookingDataService bookingDataService;

    @FXML
    private DatePicker fromDatePicker;
    private LocalDate selectedFromDate;
    private final LocalDate fromDateMin = LocalDate.now();
    private LocalDate fromDateMax = LocalDate.MAX;

    @FXML
    private Button germanButton;
    @FXML
    private Button englishButton;
    @FXML
    private DatePicker toDatePicker;
    private LocalDate selectedToDate;
    private LocalDate toDateMin = LocalDate.now().plusDays(1);
    private final LocalDate toDateMax = LocalDate.MAX;

    @FXML
    private ChoiceBox<RoomTypeEntity> roomTypePicker;
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
    @FXML
    private Button bookingCreationButton;


    public BookingCreationController(){
        this.bookingDataService = new BookingDataServiceImpl();
        ResourceBundle langBundle = ResourceBundle.getBundle(ResourcePaths.LANGUAGE_BUNDLE);
    }

    public void initialize(){
        roomTypePicker.setOnAction(this::handleOnRoomTypePicked);

        setChoiceBoxConverters();
        setTextFieldFormatters();

        loadValuesIntoPickers();
        setupDatePickers();
    }

    private void loadValuesIntoPickers(){
        RoomTypeEntity roomType= roomTypePicker.getValue();

        componentContentLoader.loadRoomTypes(roomTypePicker);
        componentContentLoader.loadFreeRooms(roomNumberPicker, roomType, selectedFromDate, selectedToDate);
        componentContentLoader.loadCateringTypes(cateringTypePicker);
    }

    private void setupDatePickers(){
        // Allow only certain fields of the datePickers to be picked
        Callback<DatePicker, DateCell> startDayCellFactory = super.getDayCellFactory(fromDateMin, fromDateMax);
        Callback<DatePicker, DateCell> endDayCellFactory = getDayCellFactory(toDateMin, toDateMax);
        fromDatePicker.setDayCellFactory(startDayCellFactory);
        toDatePicker.setDayCellFactory(endDayCellFactory);

        // Note: handleOnBookingCreationButtonClicked is assigned in .fxml as it does not work in code
        fromDatePicker.setOnAction(this::handleOnFromDatePicked);
        toDatePicker.setOnAction(this::handleOnToDatePicked);
    }

    private void setTextFieldFormatters(){
        adultsNumberPicker.setTextFormatter(new DecimalTextFormatter(0, 0, false));
        childrenNumberPicker.setTextFormatter(new DecimalTextFormatter(0, 0, false));
        birthDayTextField.setTextFormatter(new DecimalTextFormatter(0, 0, false));
        birthMonthTextField.setTextFormatter(new DecimalTextFormatter(0, 0, false));
        birthYearTextField.setTextFormatter(new DecimalTextFormatter(0, 0, false));
        postcodeTextField.setTextFormatter(new DecimalTextFormatter(0, 0, false));

        // To show promptText clear Fields
        birthDayTextField.setText("");
        birthMonthTextField.setText("");
        birthYearTextField.setText("");
        postcodeTextField.setText("");
    }

    private void setChoiceBoxConverters(){
        roomTypePicker.setConverter(new RoomTypeConverter());
        cateringTypePicker.setConverter(new CateringTypeEntityConverter());
        roomNumberPicker.setConverter(new RoomEntityConverter());
    }

    @FXML
    private void handleOnRoomTypePicked(ActionEvent event){
        RoomTypeEntity roomType= roomTypePicker.getValue();
        RoomEntity alreadySelectedRoom = roomNumberPicker.getValue();

        if(alreadySelectedRoom != null){
            if (alreadySelectedRoom.getType() != roomType) {
                roomNumberPicker.setValue(null);
            }
        }

        componentContentLoader.loadFreeRooms(roomNumberPicker, roomType, selectedFromDate, selectedToDate);
    }

    @FXML
    private void handleOnFromDatePicked(ActionEvent event){
        DatePicker picker = (DatePicker) event.getSource();
        selectedFromDate = picker.getValue();

        // So selected start date < end date
        toDateMin = selectedFromDate;
        roomNumberPicker.setValue(null);
        loadValuesIntoPickers();
    }

    @FXML
    private void handleOnToDatePicked(ActionEvent event){
        DatePicker picker = (DatePicker) event.getSource();
        selectedToDate = picker.getValue();

        // So selected start date < end date
        fromDateMax = selectedToDate;
        roomNumberPicker.setValue(null);
        loadValuesIntoPickers();
    }

    @FXML
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

        // No try and catch needed as formatter only allows numbers
        adultsNumber = Integer.parseInt(adultNumberString);
        childrenNumber = Integer.parseInt(childNumberString);

        try {
            birthDay = Integer.parseInt(birthDayTextField.getText());
            birthMonth = Integer.parseInt(birthMonthTextField.getText());
            birthYear = Integer.parseInt(birthYearTextField.getText());
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.WARNING, langBundle.getString("onlyNumbersForDateAllowedMessage")).showAndWait();
            return;
        }

        //check if date is valid
        if (birthMonth > 12 || birthDay > 31) {
            new Alert(Alert.AlertType.WARNING, langBundle.getString("enterValidDateMessage")).showAndWait();
            return;
        }

        // If input of guest numbers is not valid
        if(!checkNumberOfGuestsValidity(adultsNumber, childrenNumber, room)){
            new Alert(Alert.AlertType.WARNING,
                    langBundle.getString("tooManyGuestsForRoomMessage") + " " + room.getType().getMaxPersons()
            ).showAndWait();
            return;
        }

        // TODO implement country

        Date birthDate = new Date(birthYear-1900, birthMonth, birthDay);
        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        int totalGuestNumber = childrenNumber + adultsNumber;

        // TODO check if guest already existing and replace dateString
        AddressEntity address = new AddressEntity(streetName, streetNumber, place, postcode, "Germany");
        GuestEntity guest = new GuestEntity(firstName, lastName, birthDate, address, telephoneNumber, "", emailAddress);
        RoomBookingEntity roomBooking = new RoomBookingEntity(guest, room, DateUtils.asDate(selectedFromDate), DateUtils.asDate(selectedToDate), adultsNumber, childrenNumber, timestamp, "");
        Set<CateringBookingEntity> cateringBookings = createCateringBookings(totalGuestNumber, roomBooking, cateringType, fromDate, toDate);

        bookingDataService.persistAddress(address);
        bookingDataService.persistGuest(guest);
        bookingDataService.persistRoomBooking(roomBooking);
        bookingDataService.persistCateringBookings(cateringBookings);
    }

    private Set<CateringBookingEntity> createCateringBookings(int number, RoomBookingEntity roomBooking, CateringTypeEntity cateringType, Date startDate, Date endDate){
        Set<CateringBookingEntity> cateringBookings = new HashSet<>();
        CateringBookingEntity tmp;

        for (int i = 0; i < number; i++){
            tmp = new CateringBookingEntity();
            tmp.setCateringType(cateringType);
            tmp.setRoomBooking(roomBooking);
            tmp.setStartDate(startDate);
            tmp.setEndDate(endDate);
            cateringBookings.add(tmp);
        }

        return cateringBookings;
    }

    private boolean checkNumberOfGuestsValidity(int adultsNumber, int childrenNumber, RoomEntity room){
        int personNumber = adultsNumber + childrenNumber;
        if (personNumber > room.getType().getMaxPersons()){
            return false;
        }
        return true;
    }
}
