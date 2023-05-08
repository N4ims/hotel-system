package com.n4ims.hotelsystem.controllers.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.n4ims.hotelsystem.entities.CateringTypeEntity;
import org.junit.jupiter.api.Test;

public class CateringTypeEntityConverterTest {

    @Test
    public void testToString() {
        CateringTypeEntityConverter converter = new CateringTypeEntityConverter();
        CateringTypeEntity cateringType = new CateringTypeEntity();
        cateringType.setType("Buffet");
        String expectedString = "Buffet";
        String actualString = converter.toString(cateringType);
        assertEquals(expectedString, actualString);
    }

    @Test
    public void testToStringWithNull() {
        CateringTypeEntityConverter converter = new CateringTypeEntityConverter();
        CateringTypeEntity cateringType = null;
        String expectedString = "";
        String actualString = converter.toString(cateringType);
        assertEquals(expectedString, actualString);
    }


    // FromString Method to be implemented

//    @Test
//    public void testFromString() {
//        CateringTypeEntityConverter converter = new CateringTypeEntityConverter();
//        String string = "Buffet";
//        CateringTypeEntity expectedCateringType = new CateringTypeEntity();
//        expectedCateringType.setType("Buffet");
//        CateringTypeEntity actualCateringType = converter.fromString(string);
//        assertEquals(expectedCateringType, actualCateringType);
//    }
//
    @Test
    public void testFromStringWithNull() {
        CateringTypeEntityConverter converter = new CateringTypeEntityConverter();
        String string = null;
        CateringTypeEntity expectedCateringType = null;
        CateringTypeEntity actualCateringType = converter.fromString(string);
        assertEquals(expectedCateringType, actualCateringType);
    }
}