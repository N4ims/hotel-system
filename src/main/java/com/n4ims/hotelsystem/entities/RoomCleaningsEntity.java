package com.n4ims.hotelsystem.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "room_cleanings", schema = "hotel_system")
public class RoomCleaningsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "room_id")
    private Integer roomId;
    @Basic
    @Column(name = "cleaning_personnel_id")
    private Integer cleaningPersonnelId;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "time")
    private Time time;
    @Basic
    @Column(name = "cleaning_type_id")
    private Integer cleaningTypeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getCleaningPersonnelId() {
        return cleaningPersonnelId;
    }

    public void setCleaningPersonnelId(Integer cleaningPersonnelId) {
        this.cleaningPersonnelId = cleaningPersonnelId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Integer getCleaningTypeId() {
        return cleaningTypeId;
    }

    public void setCleaningTypeId(Integer cleaningTypeId) {
        this.cleaningTypeId = cleaningTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCleaningsEntity that = (RoomCleaningsEntity) o;
        return id == that.id && Objects.equals(roomId, that.roomId) && Objects.equals(cleaningPersonnelId, that.cleaningPersonnelId) && Objects.equals(date, that.date) && Objects.equals(time, that.time) && Objects.equals(cleaningTypeId, that.cleaningTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomId, cleaningPersonnelId, date, time, cleaningTypeId);
    }
}
