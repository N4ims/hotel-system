package com.n4ims.hotelsystem.persistence;

import com.n4ims.hotelsystem.entities.AddressEntity;
import com.n4ims.hotelsystem.entities.GuestEntity;
import com.n4ims.hotelsystem.entities.RoomBookingEntity;

import java.sql.Date;
import java.util.List;

public interface BookingDataService {
    /**
     * This method will return all bookings that have their start date in the given period.
     * @param startDate inclusive
     * @param endDate exclusive, bookings that start at or follow after this date will not be included
     * @return all bookings for the given period
     */
    List<RoomBookingEntity> getAllBookingsForPeriod(Date startDate, Date endDate);

    /**
     * This method will return all rooms that have no bookings in between the given period.
     * @param startDate inclusive
     * @param endDate exclusive, bookings that start at or follow after this date will not be included
     * @return all rooms that have no booking in the given period
     */
    List<RoomBookingEntity> getAllFreeRoomsForPeriod(Date startDate, Date endDate);

    void createRoomBooking(RoomBookingEntity booking);
    void updateRoomBooking(RoomBookingEntity booking);
    void createAddress(AddressEntity address);
    void createGuest(GuestEntity guest);



}
