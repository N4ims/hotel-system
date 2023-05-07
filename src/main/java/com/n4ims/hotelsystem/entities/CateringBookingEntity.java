package com.n4ims.hotelsystem.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "catering_bookings", schema = "hotel_system")
public class CateringBookingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "catering_type_id")
    private CateringTypeEntity cateringType;
    @ManyToOne
    @JoinColumn(name = "room_booking_id")
    private RoomBookingEntity roomBooking;
    @Basic
    @Column(name = "start_date")
    private Date startDate;
    @Basic
    @Column(name = "end_date")
    private Date endDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CateringTypeEntity getCateringType() {
        return cateringType;
    }

    public void setCateringType(CateringTypeEntity cateringType) {
        this.cateringType = cateringType;
    }

    public RoomBookingEntity getRoomBooking() {
        return roomBooking;
    }

    public void setRoomBooking(RoomBookingEntity bookingId) {
        this.roomBooking = bookingId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public CateringBookingEntity(){};

    public CateringBookingEntity(CateringTypeEntity cateringType, RoomBookingEntity roomBooking, Date startDate, Date endDate) {
        this.cateringType = cateringType;
        this.roomBooking = roomBooking;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CateringBookingEntity that = (CateringBookingEntity) o;
        return id == that.id && Objects.equals(cateringType, that.cateringType) && Objects.equals(roomBooking, that.roomBooking) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cateringType, roomBooking, startDate, endDate);
    }

    /**
     Checks the validity of the number of guests for the selected room.
     @param adultsNumber the number of adults entered by the user
     @param childrenNumber the number of children entered by the user
     @param room the room selected by the user
     @return true if the number of guests is valid for the selected room, false otherwise
    */
    public static Set<CateringBookingEntity> createCateringBookings(int number, RoomBookingEntity roomBooking, CateringTypeEntity cateringType, Date startDate, Date endDate){
        Set<CateringBookingEntity> cateringBookings = new HashSet<>();
        CateringBookingEntity tmp;

        for (int i = 0; i < number; i++){
            tmp = new CateringBookingEntity();
            tmp.setCateringType(cateringType);
            tmp.setRoomBooking(roomBooking);
            tmp.setStartDate(startDate);
            tmp.setEndDate(endDate);
            cateringBookings.add(tmp);
        }

        return cateringBookings;
    }
}
