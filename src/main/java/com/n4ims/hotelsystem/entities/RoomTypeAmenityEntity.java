package com.n4ims.hotelsystem.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "room_type_amenities", schema = "hotel_system")
public class RoomTypeAmenityEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomTypeEntity roomType;
    @ManyToOne
    @JoinColumn(name = "amenity_id")
    private AmenityEntity amenity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoomTypeEntity getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomTypeEntity roomTypeId) {
        this.roomType = roomTypeId;
    }

    public AmenityEntity getAmenity() {
        return amenity;
    }

    public void setAmenity(AmenityEntity amenityId) {
        this.amenity = amenityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomTypeAmenityEntity that = (RoomTypeAmenityEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(roomType, that.roomType)) return false;
        return Objects.equals(amenity, that.amenity);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (roomType != null ? roomType.hashCode() : 0);
        result = 31 * result + (amenity != null ? amenity.hashCode() : 0);
        return result;
    }
}
