package com.n4ims.hotelsystem.controllers.converters;

import com.n4ims.hotelsystem.entities.RoomEntity;
import com.n4ims.hotelsystem.entities.RoomTypeEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RoomEntityConverterTest {

    @Test
    void testToString() {
        RoomEntity roomEntity = new RoomEntity();
        RoomTypeEntity roomTypeEntity = new RoomTypeEntity();


        roomEntity.setId(1);

        roomTypeEntity.setType("Single Room");
        roomEntity.setType(roomTypeEntity);

        RoomEntityConverter roomEntityConverter = new RoomEntityConverter();
        String expected = "Zimmer 1, Single Room";
        String actual = roomEntityConverter.toString(roomEntity);

        Assertions.assertEquals(expected, actual);
    }
}
