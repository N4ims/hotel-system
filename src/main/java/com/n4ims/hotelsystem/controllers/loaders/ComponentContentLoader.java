package com.n4ims.hotelsystem.controllers.loaders;

import com.n4ims.hotelsystem.entities.CateringTypeEntity;
import com.n4ims.hotelsystem.entities.RoomEntity;
import com.n4ims.hotelsystem.entities.RoomTypeEntity;
import javafx.scene.control.ChoiceBox;

import java.time.LocalDate;

public interface ComponentContentLoader {
    void loadRoomTypes(ChoiceBox<RoomTypeEntity> choiceBox);
    void loadFreeRooms(ChoiceBox<RoomEntity> choiceBox, RoomTypeEntity roomType, LocalDate fromLocalDate, LocalDate toLocalDate);
    void loadFreeRooms(ChoiceBox<RoomEntity> choiceBox, LocalDate fromLocalDate, LocalDate toLocalDate);
    void loadCateringTypes(ChoiceBox<CateringTypeEntity> choiceBox);

    //TODO add packages
    //void loadCateringTypes(ChoiceBox<PackageEntity> choiceBox);
}
