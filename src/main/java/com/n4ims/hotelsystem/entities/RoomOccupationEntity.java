package com.n4ims.hotelsystem.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "room_occupations", schema = "hotel_system")
public class RoomOccupationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @OneToOne
    @JoinColumn(name = "room_booking_id")
    private RoomBookingEntity roomBooking;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;
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

    public RoomBookingEntity getRoomBooking() {
        return roomBooking;
    }

    public void setRoomBooking(RoomBookingEntity bookingId) {
        this.roomBooking = bookingId;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity roomId) {
        this.room = roomId;
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
        RoomOccupationEntity that = (RoomOccupationEntity) o;
        return id == that.id && Objects.equals(roomBooking, that.roomBooking) && Objects.equals(room, that.room) && Objects.equals(checkIn, that.checkIn) && Objects.equals(checkOut, that.checkOut) && Objects.equals(deposit, that.deposit) && Objects.equals(roomLeftBehindAcceptable, that.roomLeftBehindAcceptable) && Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomBooking, room, checkIn, checkOut, deposit, roomLeftBehindAcceptable, notes);
    }
}
