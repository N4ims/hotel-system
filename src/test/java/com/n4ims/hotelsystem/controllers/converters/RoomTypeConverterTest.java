package com.n4ims.hotelsystem.controllers.converters;

import com.n4ims.hotelsystem.entities.RoomTypeEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RoomTypeConverterTest {

    private final RoomTypeConverter converter = new RoomTypeConverter();

    @Test
    public void testToString() {
        RoomTypeEntity roomTypeEntity = new RoomTypeEntity();
        roomTypeEntity.setType("Single Room");

        String expected = "Single Room";
        String result = converter.toString(roomTypeEntity);

        assertEquals(expected, result);
    }

    @Test
    public void testToStringWithNullInput() {
        String expected = "";
        String result = converter.toString(null);

        assertEquals(expected, result);
    }

    @Test
    public void testFromStringWithNullInput() {
        RoomTypeEntity result = converter.fromString(null);

        assertNull(result);
    }

}
