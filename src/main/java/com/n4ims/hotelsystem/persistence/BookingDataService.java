package com.n4ims.hotelsystem.persistence;

import com.n4ims.hotelsystem.entities.*;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.JDBCConnectionException;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface BookingDataService {
    /**
     * This method will return all bookings that have their start date in the given period.
     * @param fromDate inclusive
     * @param toDate exclusive, bookings that start at or follow after this date will not be included
     * @return all bookings for the given period
     */
    List<RoomBookingEntity> getAllBookingsForPeriod(Date fromDate, Date toDate);

    /**
     * This method will return all rooms that have no bookings in between the given period.
     * @param fromDate inclusive
     * @param toDate exclusive, bookings that start at or follow after this date will not be included
     * @return all rooms that have no booking in the given period
     */

    List<RoomEntity> getAllFreeRoomsForPeriod(RoomTypeEntity roomType, Date fromDate, Date toDate) throws JDBCConnectionException;
    List<CateringTypeEntity> getAllCateringTypes() throws JDBCConnectionException;
    List<RoomTypeEntity> getAllRoomTypes() throws JDBCConnectionException;
    void persistRoomBooking(RoomBookingEntity booking) throws PersistenceException;
    void persistAddress(AddressEntity address) throws PersistenceException;
    void persistGuest(GuestEntity guest) throws PersistenceException;
    void persistCateringBookings(Set<CateringBookingEntity> cateringBookings) throws PersistenceException;



}
