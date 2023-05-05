package com.n4ims.hotelsystem.controllers.loaders;

import com.n4ims.hotelsystem.entities.CateringTypeEntity;
import com.n4ims.hotelsystem.entities.RoomEntity;
import com.n4ims.hotelsystem.entities.RoomTypeEntity;
import jakarta.persistence.PersistenceException;
import javafx.scene.control.ChoiceBox;

import java.time.LocalDate;
import java.util.ResourceBundle;

public interface ComponentContentLoader {

    /**
     * This method will load RoomTypesEntities into the given choicebox
     * @param choiceBox component to load the rooms into
     * @throws PersistenceException
     */
    void loadRoomTypes(ChoiceBox<RoomTypeEntity> choiceBox) throws PersistenceException;
    void loadFreeRooms(ChoiceBox<RoomEntity> choiceBox, RoomTypeEntity roomType, LocalDate fromLocalDate, LocalDate toLocalDate) throws PersistenceException;
    void loadFreeRooms(ChoiceBox<RoomEntity> choiceBox, LocalDate fromLocalDate, LocalDate toLocalDate) throws PersistenceException;
    void loadCateringTypes(ChoiceBox<CateringTypeEntity> choiceBox) throws PersistenceException;

    //TODO add packages
}
