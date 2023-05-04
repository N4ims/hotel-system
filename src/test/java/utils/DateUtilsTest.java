package utils;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DateUtilsTest {

    @Test
    public void testAsDate() {
        // Mock LocalDate and ZoneId
        LocalDate localDate = mock(LocalDate.class);
        ZoneId zoneId = mock(ZoneId.class);

        // Set up mock LocalDate and ZoneId to return fixed values
        when(localDate.atStartOfDay()).thenReturn(LocalDateTime.of(2023, 05, 01, 1 ,1));
        when(zoneId.systemDefault()).thenReturn(ZoneId.of("ECT"));

        // Call the method being tested
        Date result = DateUtils.asDate(localDate);

        Date checkDate = new Date(1683097119);
        // Check that the result is as expected
        assertEquals(checkDate, result);
    }
}