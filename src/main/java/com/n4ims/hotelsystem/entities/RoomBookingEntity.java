package com.n4ims.hotelsystem.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "room_bookings", schema = "hotel_system")
public class RoomBookingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private GuestEntity guest;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;
    @Basic
    @Column(name = "from_date")
    private Date fromDate;
    @Basic
    @Column(name = "to_date")
    private Date toDate;
    @Basic
    @Column(name = "number_of_adults")
    private Integer numberOfAdults;
    @Basic
    @Column(name = "number_of_children")
    private Integer numberOfChildren;

    @OneToMany(mappedBy = "roomBooking")
    private Set<CateringBookingEntity> cateringBookings;

    @Basic
    @Column(name = "timestamp")
    private Timestamp timestamp;
    @Basic
    @Column(name = "notes")
    private String notes;

    public RoomBookingEntity(){}
    public RoomBookingEntity(GuestEntity guest, RoomEntity room, Date fromDate, Date toDate, Integer numberOfAdults, Integer numberOfChildren, Timestamp timestamp, String notes) {
        this.guest = guest;
        this.room = room;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.timestamp = timestamp;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GuestEntity getGuest() {
        return guest;
    }

    public void setGuest(GuestEntity guestId) {
        this.guest = guestId;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity roomId) {
        this.room = roomId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(Integer numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public Integer getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(Integer numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public Set<CateringBookingEntity> getCateringBookings() {
        return cateringBookings;
    }

    public void setCateringBookings(Set<CateringBookingEntity> cateringBookings) {
        this.cateringBookings = cateringBookings;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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
        RoomBookingEntity that = (RoomBookingEntity) o;
        return id == that.id && Objects.equals(guest, that.guest) && Objects.equals(room, that.room) && Objects.equals(fromDate, that.fromDate) && Objects.equals(toDate, that.toDate) && Objects.equals(numberOfAdults, that.numberOfAdults) && Objects.equals(numberOfChildren, that.numberOfChildren) && Objects.equals(cateringBookings, that.cateringBookings) && Objects.equals(timestamp, that.timestamp) && Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, guest, room, fromDate, toDate, numberOfAdults, numberOfChildren, cateringBookings, timestamp, notes);
    }
}
