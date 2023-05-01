package com.n4ims.hotelsystem.persistence;

import com.n4ims.hotelsystem.entities.AddressEntity;
import com.n4ims.hotelsystem.entities.GuestEntity;
import com.n4ims.hotelsystem.entities.RoomBookingEntity;
import com.n4ims.hotelsystem.entities.RoomEntity;

import java.util.Date;
import java.util.List;

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
    List<RoomEntity> getAllFreeRoomsForPeriod(Date fromDate, Date toDate);

    void createRoomBooking(RoomBookingEntity booking);
    void updateRoomBooking(RoomBookingEntity booking);
    void createAddress(AddressEntity address);
    void createGuest(GuestEntity guest);



}
