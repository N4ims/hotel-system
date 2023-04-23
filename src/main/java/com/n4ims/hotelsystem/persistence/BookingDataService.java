package com.n4ims.hotelsystem.persistence;

import com.n4ims.hotelsystem.entities.AddressesEntity;
import com.n4ims.hotelsystem.entities.GuestsEntity;
import com.n4ims.hotelsystem.entities.RoomBookingsEntity;

import java.sql.Date;
import java.util.List;

public interface BookingDataService {
    /**
     * This method will return all bookings that have their start date in the given period.
     * @param startDate inclusive
     * @param endDate exclusive, bookings that start at or follow after this date will not be included
     * @return
     */
    List<RoomBookingsEntity> getAllBookingsForPeriod(Date startDate, Date endDate);

    /**
     * This method will return all rooms that have no bookings in between the given period.
     * @param startDate inclusive
     * @param endDate exclusive, bookings that start at or follow after this date will not be included
     * @return
     */
    List<RoomBookingsEntity> getAllFreeRoomsForPeriod(Date startDate, Date endDate);

    void createRoomBooking(RoomBookingsEntity booking);
    void updateRoomBooking(RoomBookingsEntity booking);
    void createAddress(AddressesEntity address);
    void createGuest(GuestsEntity guest);



}
