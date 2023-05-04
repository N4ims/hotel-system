package com.n4ims.hotelsystem.controllers.loaders;

import com.n4ims.hotelsystem.entities.CateringTypeEntity;
import com.n4ims.hotelsystem.entities.RoomEntity;
import com.n4ims.hotelsystem.entities.RoomTypeEntity;
import com.n4ims.hotelsystem.persistence.BookingDataService;
import com.n4ims.hotelsystem.persistence.BookingDataServiceImpl;
import jakarta.persistence.NoResultException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DateUtils;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ComponentContentLoaderImpl implements ComponentContentLoader{
    public static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    BookingDataService bookingDataService;
    public ComponentContentLoaderImpl(){
        bookingDataService = new BookingDataServiceImpl();
    }

    @Override
    public void loadRoomTypes(ChoiceBox<RoomTypeEntity> choiceBox) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                List<RoomTypeEntity> c;

                try{
                    c = bookingDataService.getAllRoomTypes();
                } catch (JDBCConnectionException e){
                    log.error("Database cannot be reached.");
                    throw e;
                }

                ObservableList<RoomTypeEntity> cateringTypes = FXCollections.observableList(c);
                choiceBox.setItems(cateringTypes);
            }
        };

        thread.start();
    }

    public void loadFreeRooms(ChoiceBox<RoomEntity> choiceBox, RoomTypeEntity roomType, LocalDate fromLocalDate, LocalDate toLocalDate){
        Thread thread = new Thread(){

            @Override
            public void run() {
                java.util.Date fromDate = null;
                java.util.Date toDate = null;

                if (fromLocalDate != null) {
                    fromDate = DateUtils.asDate(fromLocalDate);
                }

                if(toLocalDate != null) {
                    toDate = DateUtils.asDate(toLocalDate);
                }

                List<RoomEntity> r;
                try{
                    r = bookingDataService.getAllFreeRoomsForPeriod(roomType, fromDate, toDate);
                } catch (JDBCConnectionException e){
                    log.error("Database cannot be reached.", e);
                    throw e;
                }

                // TODO notify user if no rooms are free for period

                ObservableList<RoomEntity> freeRooms = FXCollections.observableList(r);
                choiceBox.setItems(freeRooms);
            }
        };

        thread.start();
    }

    public void loadFreeRooms(ChoiceBox<RoomEntity> choiceBox, LocalDate fromLocalDate, LocalDate toLocalDate){
        loadFreeRooms(choiceBox, null, fromLocalDate, toLocalDate);
    }

    public void loadCateringTypes(ChoiceBox<CateringTypeEntity> choiceBox){
        Thread thread = new Thread(){
            @Override
            public void run() {
                List<CateringTypeEntity> c;

                try{
                    c = bookingDataService.getAllCateringTypes();
                } catch (JDBCConnectionException e){
                    log.error("Database cannot be reached.");
                    throw e;
                }

                ObservableList<CateringTypeEntity> cateringTypes = FXCollections.observableList(c);
                choiceBox.setItems(cateringTypes);
            }
        };

        thread.start();
    }
}
