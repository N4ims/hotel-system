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

/**
 * This controller handles the creation of new bookings.
 */
public class BookingCreationController extends BasicController{

    //
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

    /**
     * Constructs a new BookingCreationController object.
     */
    public BookingCreationController(){
        this.bookingDataService = new BookingDataServiceImpl();
        langBundle = ResourceBundle.getBundle(ResourcePaths.LANGUAGE_BUNDLE);
    }

    /**
     * Initializes the BookingCreationController object.
     */
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

    /**
     Loads values into pickers based on selected room type, selected from and to dates, and catering types.
     */
    private void loadValuesIntoPickers(){

        // Retrieve the currently selected room type
        RoomTypeEntity roomType= roomTypePicker.getValue();

        try{
            // Load available room types into the room type picker
            componentContentLoader.loadRoomTypes(roomTypePicker);
            // Load available rooms of the selected type and within the selected date range into the room number picker
            componentContentLoader.loadFreeRooms(roomNumberPicker, roomType, selectedFromDate, selectedToDate);
            // Load available catering types into the catering type picker
            componentContentLoader.loadCateringTypes(cateringTypePicker);
        } catch (PersistenceException e){
            // Log the error and show an error alert if there was a problem accessing the database
            log.error("Error while trying to access database", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setContent(new Text(langBundle.getString("databaseErrorMessage")));
            alert.showAndWait();
        }
    }


    private void setupDatePickers(){
        Callback<DatePicker, DateCell> startDayCellFactory = getDayCellFactory(fromDateMin, fromDateMax);
        Callback<DatePicker, DateCell> endDayCellFactory = getDayCellFactory(toDateMin, toDateMax);
        fromDatePicker.setDayCellFactory(startDayCellFactory);
        toDatePicker.setDayCellFactory(endDayCellFactory);

        fromDatePicker.setOnAction(this::handleOnFromDatePicked);
        toDatePicker.setOnAction(this::handleOnToDatePicked);
    }


    /**

     Sets up text field formatters for certain text fields to restrict user input to integer values only.
     Specifically, sets DecimalTextFormatter with no decimal places, minimum and maximum integer value of 0,
     and disallows negative values for the following text fields: adultsNumberPicker, childrenNumberPicker,
     birthDayTextField, birthMonthTextField, birthYearTextField, postcodeTextField.
     Also clears the fields of their default TextFormatter value to show promptText.
     */
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

    /**

     Sets the converters for the choice box pickers.
     The converters are used to convert between the displayed value and the actual object value in the choice box.
     For example, the RoomTypeConverter converts between RoomTypeEntity objects and their String representation.
     */
    private void setChoiceBoxConverters(){
        roomTypePicker.setConverter(new RoomTypeConverter());
        cateringTypePicker.setConverter(new CateringTypeEntityConverter());
        roomNumberPicker.setConverter(new RoomEntityConverter());
    }

    /**

     Handles the event when a new room type is selected from the roomTypePicker.

     Loads the available free rooms of the selected type into the roomNumberPicker.

     Clears the value of the roomNumberPicker if the already selected room has a different type.

     @param event The event triggered by selecting a new room type from the roomTypePicker.
     */
    @FXML
    private void handleOnRoomTypePicked(ActionEvent event){
        RoomTypeEntity roomType = roomTypePicker.getValue();
        RoomEntity alreadySelectedRoom = roomNumberPicker.getValue();

        // deselect picked room number if room type has changed
        if(alreadySelectedRoom != null){
            if (alreadySelectedRoom.getType() != roomType) {
                roomNumberPicker.setValue(null);
            }
        }

        componentContentLoader.loadFreeRooms(roomNumberPicker, roomType, selectedFromDate, selectedToDate);
    }

    /**

     Handles the event when the user picks a date from the "from" date picker.

     @param event the event triggered by the user picking a date
     */
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

    /**
     *
     Handles the event when a date is picked in the toDatePicker.

     Sets the selectedToDate field and updates the fromDateMax field to ensure that the selectedFromDate is always before the selectedToDate.

     Also clears the selected room number and reloads the room number picker and other pickers with new values based on the updated date range.

     @param event the event triggered by picking a date in the toDatePicker

     */
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


    /**

     Handles the event when the "Create booking" button is clicked. Collects all necessary data from the input fields,

     validates it and creates a new booking entity in the database. Displays warning messages if input is invalid.

     @param event the ActionEvent triggered by clicking the "Create booking" button
     */
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
        String country = countryTextField.getText();
        RoomEntity room = roomNumberPicker.getValue();
        String notes = notesTextArea.getText();

        int adultsNumber;
        int childrenNumber;
        int birthDay;
        int birthMonth;
        int birthYear;

        // No try and catch needed as formatter only allows numbers
        try{
            adultsNumber = Integer.parseInt(adultNumberString);
            childrenNumber = Integer.parseInt(childNumberString);
        } catch (NumberFormatException e){
            new Alert(Alert.AlertType.WARNING, langBundle.getString("selectNumberOfGuestsMessage")).showAndWait();
            return;
        }

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

        if (firstName.isEmpty() || lastName.isEmpty() || postcode.isEmpty() || place.isEmpty()
                || country.isEmpty() || streetName.isEmpty() || streetNumber .isEmpty())
        {
            new Alert(Alert.AlertType.WARNING,
                    langBundle.getString("enterAllGuestInformationFieldsMessage")
            ).showAndWait();
            return;
        }

        // only one of both is needed
        if (telephoneNumber.isEmpty() && emailAddress.isEmpty()){
            new Alert(Alert.AlertType.WARNING,
                    langBundle.getString("enterPhoneNumberOrEmailMessage")
            ).showAndWait();
            return;
        }

        if (!checkBirthdayValidity(birthMonth, birthDay)){
            return;
        }

        Date birthDate = new Date(birthYear-1900, birthMonth, birthDay);
        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        int totalGuestNumber = childrenNumber + adultsNumber;

        AddressEntity address = new AddressEntity(streetName, streetNumber, place, postcode, country);
        GuestEntity guest = new GuestEntity(firstName, lastName, birthDate, address, telephoneNumber, "", emailAddress);
        RoomBookingEntity roomBooking = new RoomBookingEntity(guest, room, DateUtils.asDate(selectedFromDate), DateUtils.asDate(selectedToDate), adultsNumber, childrenNumber, timestamp, notes);
        Set<CateringBookingEntity> cateringBookings = CateringBookingEntity.createCateringBookings(totalGuestNumber, roomBooking, cateringType, fromDate, toDate);

        try{
            bookingDataService.persistBooking(address, guest, roomBooking, cateringBookings);
            new Alert(Alert.AlertType.CONFIRMATION,
                    langBundle.getString("bookingCreatedMessage")
            ).showAndWait();
            return;
        } catch (PersistenceException e){
            log.error("Error when trying to persist booking.", e);
            new Alert(Alert.AlertType.WARNING,
                    langBundle.getString("bookingCreationErrorMessage")
            ).showAndWait();
            return;
        }
    }

    /**
     Checks the validity of the number of guests for the selected room.
     @param adultsNumber the number of adults entered by the user
     @param childrenNumber the number of children entered by the user
     @param room the room selected by the user
     @return true if the number of guests is valid for the selected room, false otherwise
     */
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

    /**

     Checks the validity of the selected booking dates.

     @param fromLocalDate the starting date of the booking, as a LocalDate object.

     @param toLocalDate the ending date of the booking, as a LocalDate object.

     @return true if the booking dates are valid, false otherwise.
     */
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

    /**

     Checks the validity of the given birth month and day.

     @param birthMonth the birth month to be checked

     @param birthDay the birth day to be checked

     @return true if the birth month and day are valid, false otherwise
     */
    private boolean checkBirthdayValidity(int birthMonth, int birthDay){
        //check if date is valid
        if (birthMonth > 12 || birthDay > 31) {
            new Alert(Alert.AlertType.WARNING, langBundle.getString("enterValidDateMessage")).showAndWait();
            return false;
        }

        return true;
    }
}
