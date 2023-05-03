package com.n4ims.hotelsystem.controllers.converters;

import com.n4ims.hotelsystem.entities.RoomTypeEntity;
import javafx.util.StringConverter;

public class RoomTypeConverter extends StringConverter<RoomTypeEntity> {
    @Override
    public String toString(RoomTypeEntity roomTypeEntity) {
        return roomTypeEntity == null ? "" : "" + roomTypeEntity.getType();
    }

    @Override
    public RoomTypeEntity fromString(String s) {
        return null;
    }
}