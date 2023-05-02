package com.n4ims.hotelsystem.controllers.loaders;

import com.n4ims.hotelsystem.entities.RoomEntity;
import com.n4ims.hotelsystem.persistence.BookingDataService;
import com.n4ims.hotelsystem.persistence.BookingDataServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import utils.DateUtils;

import java.time.LocalDate;
import java.util.List;

public class ComponentContentLoaderImpl implements ComponentContentLoader{
    public void loadFreeRooms(ChoiceBox choiceBox, LocalDate fromLocalDate, LocalDate toLocalDate){
        Thread thread = new Thread(){
            BookingDataService bookingDataService = new BookingDataServiceImpl();
            
            @Override
            public void run() {
                java.util.Date fromDate = null;
                java.util.Date toDate = null;

                if (fromLocalDate != null) {
                    fromDate = DateUtils.asDate(fromLocalDate);
                }

                if(toDate != null) {
                    toDate = DateUtils.asDate(toLocalDate);
                }

                List<RoomEntity> r = bookingDataService.getAllFreeRoomsForPeriod(fromDate, toDate);
                ObservableList<RoomEntity>  freeRooms = FXCollections.observableList(r);

                choiceBox.setItems(freeRooms);
            }
        };

        thread.start();
    }

    public void loadCateringTypes(ChoiceBox choiceBox){
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
            }
        };

        thread.start();
    }
}
