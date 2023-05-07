package com.n4ims.hotelsystem.controllers.loaders;

import com.n4ims.hotelsystem.entities.CateringTypeEntity;
import com.n4ims.hotelsystem.entities.RoomEntity;
import com.n4ims.hotelsystem.entities.RoomTypeEntity;
import com.n4ims.hotelsystem.persistence.BookingDataService;
import com.n4ims.hotelsystem.persistence.BookingDataServiceImpl;
import jakarta.persistence.PersistenceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.n4ims.hotelsystem.utils.DateUtils;
import java.lang.invoke.MethodHandles;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * This class is for loading Entities from the database into javafx components such as choiceboxes.
 * It uses no threading as threads are isolated and the user cannot be easily shown messages
 * in case of errors in the threads.
 */
public class ComponentContentLoaderImpl implements ComponentContentLoader{
    public static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    BookingDataService bookingDataService;
    public ComponentContentLoaderImpl(){
        bookingDataService = new BookingDataServiceImpl();
    }

    @Override
    public void loadRoomTypes(ChoiceBox<RoomTypeEntity> choiceBox) throws PersistenceException {
        List<RoomTypeEntity> c;

        try {
            c = bookingDataService.getAllRoomTypes();
            ObservableList<RoomTypeEntity> cateringTypes = FXCollections.observableList(c);
            choiceBox.setItems(cateringTypes);
        } catch (PersistenceException e) {
            log.error("Database cannot be reached.", e);
            throw e;
        }
    }

    public void loadFreeRooms(ChoiceBox<RoomEntity> choiceBox, RoomTypeEntity roomType, LocalDate fromLocalDate, LocalDate toLocalDate) throws PersistenceException{
        Date fromDate = null;
        Date toDate = null;

        if (fromLocalDate != null) {
            fromDate = DateUtils.asDate(fromLocalDate);
        }

        if(toLocalDate != null) {
            toDate = DateUtils.asDate(toLocalDate);
        }

        List<RoomEntity> r;
        try{
            r = bookingDataService.getAllFreeRoomsForPeriod(roomType, fromDate, toDate);
            ObservableList<RoomEntity> freeRooms = FXCollections.observableList(r);
            choiceBox.setItems(freeRooms);
        } catch (PersistenceException e){
            log.error("Database cannot be reached.", e);
            throw e;
        }
    }

    public void loadFreeRooms(ChoiceBox<RoomEntity> choiceBox, LocalDate fromLocalDate, LocalDate toLocalDate) throws PersistenceException {
        loadFreeRooms(choiceBox, null, fromLocalDate, toLocalDate);
    }

    public void loadCateringTypes(ChoiceBox<CateringTypeEntity> choiceBox) throws PersistenceException{
        List<CateringTypeEntity> c;

        try{
            c = bookingDataService.getAllCateringTypes();
            ObservableList<CateringTypeEntity> cateringTypes = FXCollections.observableList(c);
            choiceBox.setItems(cateringTypes);
        } catch (PersistenceException e){
            log.error("Database cannot be reached.", e);
            throw e;
        }
    }
}
