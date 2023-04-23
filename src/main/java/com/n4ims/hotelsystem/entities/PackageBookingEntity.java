package com.n4ims.hotelsystem.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "package_bookings", schema = "hotel_system")
public class PackageBookingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "package_type_id")
    private PackageTypeEntity packageType;
    @Basic
    @Column(name = "room_booking_id")
    private Integer roomBookingId;
    @Basic
    @Column(name = "for_date")
    private Date forDate;
    @Basic
    @Column(name = "timestamp")
    private Timestamp timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PackageTypeEntity getPackageType() {
        return packageType;
    }

    public void setPackageType(PackageTypeEntity packageType) {
        this.packageType = packageType;
    }

    public Integer getBookingId() {
        return roomBookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.roomBookingId = bookingId;
    }

    public Date getForDate() {
        return forDate;
    }

    public void setForDate(Date forDate) {
        this.forDate = forDate;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageBookingEntity that = (PackageBookingEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(packageType, that.packageType) && Objects.equals(roomBookingId, that.roomBookingId) && Objects.equals(forDate, that.forDate) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, packageType, roomBookingId, forDate, timestamp);
    }
}
