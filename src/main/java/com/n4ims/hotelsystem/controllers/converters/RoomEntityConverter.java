package com.n4ims.hotelsystem.controllers.converters;

import com.n4ims.hotelsystem.entities.RoomEntity;
import javafx.util.StringConverter;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class RoomEntityConverter extends StringConverter<RoomEntity> {
    @Override
    public String toString(RoomEntity roomEntity) {
        return roomEntity == null ? "" : "Zimmer " + roomEntity.getId() + ", " + roomEntity.getType().getType();
    }

    @Override
    public RoomEntity fromString(String s) {
        return null;
    }
}
