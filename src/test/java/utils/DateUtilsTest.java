package utils;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DateUtilsTest {

    @Test
    public void testAsDate() {
        // Mock LocalDate and ZoneId
        LocalDate localDate = LocalDate.of(1970, 1, 1);

        // Call the method being tested
        Date result = DateUtils.asDate(localDate);

        // Epoch Date
        Date expectedDate = new Date(0);

        // Check that the result is as expected
        assertEquals(expectedDate.toString(), result.toString());
    }
}