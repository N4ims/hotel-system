package com.n4ims.hotelsystem.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "catering_bookings", schema = "hotel_system")
public class CateringBookingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
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
}
