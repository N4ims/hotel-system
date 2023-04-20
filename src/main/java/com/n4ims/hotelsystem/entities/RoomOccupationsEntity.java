package com.n4ims.hotelsystem.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "room_occupations", schema = "hotel_system")
public class RoomOccupationsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "booking_id")
    private Integer bookingId;
    @Basic
    @Column(name = "room_id")
    private Integer roomId;
    @Basic
    @Column(name = "check_in")
    private Timestamp checkIn;
    @Basic
    @Column(name = "check_out")
    private Timestamp checkOut;
    @Basic
    @Column(name = "deposit")
    private Integer deposit;
    @Basic
    @Column(name = "room_left_behind_acceptable")
    private Byte roomLeftBehindAcceptable;
    @Basic
    @Column(name = "notes")
    private String notes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Timestamp getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Timestamp checkIn) {
        this.checkIn = checkIn;
    }

    public Timestamp getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Timestamp checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public Byte getRoomLeftBehindAcceptable() {
        return roomLeftBehindAcceptable;
    }

    public void setRoomLeftBehindAcceptable(Byte roomLeftBehindAcceptable) {
        this.roomLeftBehindAcceptable = roomLeftBehindAcceptable;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomOccupationsEntity that = (RoomOccupationsEntity) o;
        return id == that.id && Objects.equals(bookingId, that.bookingId) && Objects.equals(roomId, that.roomId) && Objects.equals(checkIn, that.checkIn) && Objects.equals(checkOut, that.checkOut) && Objects.equals(deposit, that.deposit) && Objects.equals(roomLeftBehindAcceptable, that.roomLeftBehindAcceptable) && Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookingId, roomId, checkIn, checkOut, deposit, roomLeftBehindAcceptable, notes);
    }
}
