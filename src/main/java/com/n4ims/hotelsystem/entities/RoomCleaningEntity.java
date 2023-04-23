package com.n4ims.hotelsystem.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "room_cleanings", schema = "hotel_system")
public class RoomCleaningEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "room_id")
    private Integer roomId;
    @ManyToOne
    @JoinColumn(name = "cleaning_personnel_id")
    private CleaningPersonnelEntity cleaningPersonnel;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "start_time")
    private Time startTime;
    @Basic
    @Column(name = "end_time")
    private Time endTime;
    @ManyToOne
    @JoinColumn(name = "cleaning_type_id")
    private CleaningTypeEntity cleaningType;

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

    public CleaningPersonnelEntity getCleaningPersonnel() {
        return cleaningPersonnel;
    }

    public void setCleaningPersonnel(CleaningPersonnelEntity cleaningPersonnelId) {
        this.cleaningPersonnel = cleaningPersonnelId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public CleaningTypeEntity getCleaningType() {
        return cleaningType;
    }

    public void setCleaningType(CleaningTypeEntity cleaningTypeId) {
        this.cleaningType = cleaningTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCleaningEntity that = (RoomCleaningEntity) o;
        return id == that.id && Objects.equals(roomId, that.roomId) && Objects.equals(cleaningPersonnel, that.cleaningPersonnel) && Objects.equals(date, that.date) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(cleaningType, that.cleaningType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomId, cleaningPersonnel, date, startTime, endTime, cleaningType);
    }
}
