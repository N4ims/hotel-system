package com.n4ims.hotelsystem.controllers;

import com.n4ims.hotelsystem.controllers.converters.CateringTypeEntityConverter;
import com.n4ims.hotelsystem.controllers.converters.RoomEntityConverter;
import com.n4ims.hotelsystem.controllers.converters.RoomTypeConverter;
import com.n4ims.hotelsystem.entities.*;
import com.n4ims.hotelsystem.persistence.BookingDataService;
import com.n4ims.hotelsystem.persistence.BookingDataServiceImpl;
import jakarta.persistence.PersistenceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;
import com.n4ims.hotelsystem.utils.DateUtils;
import com.n4ims.hotelsystem.utils.DecimalTextFormatter;
import javafx.scene.control.Button;
import com.n4ims.hotelsystem.utils.ResourcePaths;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

public class BookingCreationController extends BasicController{

    ResourceBundle langBundle;
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
    private TextArea notesTextArea;
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
    private TextField telephoneNumberTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField postcodeTextField;
    @FXML
    private TextField placeTextField;
    @FXML
    private TextField countryTextField;
    @FXML
    private TextField streetNameTextField;
    @FXML
    private TextField streetNumberTextField;
    @FXML
    private Button bookingCreationButton;


    public BookingCreationController(){
        this.bookingDataService = new BookingDataServiceImpl();
        langBundle = ResourceBundle.getBundle(ResourcePaths.LANGUAGE_BUNDLE);
    }

    public void initialize(){
        // enable reloading of this view when switching language
        imageHeaderController.setSourceViewPath(ResourcePaths.BOOKING_CREATION_VIEW);

        roomTypePicker.setOnAction(this::handleOnRoomTypePicked);
        bookingCreationButton.setOnAction((this::handleOnBookingCreationButtonClicked));

        setChoiceBoxConverters();
        setTextFieldFormatters();

        loadValuesIntoPickers();
        setupDatePickers();
    }

    private void loadValuesIntoPickers(){
        RoomTypeEntity roomType= roomTypePicker.getValue();

        try{
            componentContentLoader.loadRoomTypes(roomTypePicker);
            componentContentLoader.loadFreeRooms(roomNumberPicker, roomType, selectedFromDate, selectedToDate);
            componentContentLoader.loadCateringTypes(cateringTypePicker);
        } catch (PersistenceException e){
            log.error("Error while trying to access database", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setContent(new Text(langBundle.getString("databaseErrorMessage")));
            alert.showAndWait();
        }
    }

    private void setupDatePickers(){
        // Allow only certain fields of the datePickers to be picked
        Callback<DatePicker, DateCell> startDayCellFactory = getDayCellFactory(fromDateMin, fromDateMax);
        Callback<DatePicker, DateCell> endDayCellFactory = getDayCellFactory(toDateMin, toDateMax);
        fromDatePicker.setDayCellFactory(startDayCellFactory);
        toDatePicker.setDayCellFactory(endDayCellFactory);

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

        // Clear fields of default TextFormatter value to show promptText
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
        // reload disabled date tiles
        loadValuesIntoPickers();
    }

    @FXML
    private void handleOnToDatePicked(ActionEvent event){
        DatePicker picker = (DatePicker) event.getSource();
        selectedToDate = picker.getValue();

        // So selected start date < end date
        fromDateMax = selectedToDate;
        roomNumberPicker.setValue(null);
        // reload disabled date tiles
        loadValuesIntoPickers();
    }

    @FXML
    private void handleOnBookingCreationButtonClicked(ActionEvent event){
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
        String county = countryTextField.getText();
        RoomEntity room = roomNumberPicker.getValue();
        String notes = notesTextArea.getText();

        int adultsNumber;
        int childrenNumber;
        int birthDay;
        int birthMonth;
        int birthYear;


        // No try and catch needed as formatter only allows numbers
        adultsNumber = Integer.parseInt(adultNumberString);
        childrenNumber = Integer.parseInt(childNumberString);


        if (!checkInputDatesValidity(selectedFromDate, selectedToDate)){
            return;
        }
        // check if a room is selected
        if(room == null){
            new Alert(Alert.AlertType.WARNING, langBundle.getString("pickARoomMessage")).showAndWait();
            return;
        }
        if (!checkNumberOfGuestsValidity(adultsNumber, childrenNumber, room)){
            return;
        }

        Date fromDate = DateUtils.asDate(selectedFromDate);
        Date toDate = DateUtils.asDate(selectedToDate);

        // Check birthday
        try {
            birthDay = Integer.parseInt(birthDayTextField.getText());
            birthMonth = Integer.parseInt(birthMonthTextField.getText());
            birthYear = Integer.parseInt(birthYearTextField.getText());
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.WARNING, langBundle.getString("onlyNumbersForDateAllowedMessage")).showAndWait();
            return;
        }

        if (!checkBirthdayValidity(birthMonth, birthDay)){
            return;
        }

        Date birthDate = new Date(birthYear-1900, birthMonth, birthDay);
        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        int totalGuestNumber = childrenNumber + adultsNumber;

        // TODO check if guest already existing and replace dateString
        AddressEntity address = new AddressEntity(streetName, streetNumber, place, postcode, county);
        GuestEntity guest = new GuestEntity(firstName, lastName, birthDate, address, telephoneNumber, "", emailAddress);
        RoomBookingEntity roomBooking = new RoomBookingEntity(guest, room, DateUtils.asDate(selectedFromDate), DateUtils.asDate(selectedToDate), adultsNumber, childrenNumber, timestamp, notes);
        Set<CateringBookingEntity> cateringBookings = CateringBookingEntity.createCateringBookings(totalGuestNumber, roomBooking, cateringType, fromDate, toDate);

        try{
            bookingDataService.persistBooking(address, guest, roomBooking, cateringBookings);
        } catch (PersistenceException e){
            log.error("Error when trying to persist booking.", e);
            new Alert(Alert.AlertType.WARNING,
                    langBundle.getString("bookingCreationErrorMessage")
            ).showAndWait();
        }
    }

    private boolean checkNumberOfGuestsValidity(int adultsNumber, int childrenNumber, RoomEntity room){
        int personNumber = adultsNumber + childrenNumber;

        if (personNumber == 0){
            new Alert(Alert.AlertType.WARNING,
                    langBundle.getString("selectNumberOfGuestsMessage")
            ).showAndWait();
            return false;
        }

        if (personNumber > room.getType().getMaxPersons()){
            new Alert(Alert.AlertType.WARNING,
                    langBundle.getString("tooManyGuestsForRoomMessage") + " " + room.getType().getMaxPersons()
            ).showAndWait();
            return false;
        }

        return true;
    }

    private boolean checkInputDatesValidity(LocalDate fromLocalDate, LocalDate toLocalDate){
        Date fromDate;
        Date toDate;
        try{
            fromDate = DateUtils.asDate(fromLocalDate);
            toDate = DateUtils.asDate(toLocalDate);
        } catch (NullPointerException e){
            new Alert(Alert.AlertType.WARNING, langBundle.getString("selectBothDates")).showAndWait();
            return false;
        }


        // check if booking timeframe validity
        if (!toDate.after(fromDate)){
            new Alert(Alert.AlertType.WARNING, langBundle.getString("startDateGreaterThanToDateMessage")).showAndWait();
            return false;
        }

        return true;
    }

    private boolean checkBirthdayValidity(int birthMonth, int birthDay){
        //check if date is valid
        if (birthMonth > 12 || birthDay > 31) {
            new Alert(Alert.AlertType.WARNING, langBundle.getString("enterValidDateMessage")).showAndWait();
            return false;
        }

        return true;
    }
}
