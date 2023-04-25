package com.n4ims.hotelsystem.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "rooms", schema = "hotel_system")
public class RoomEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private RoomTypeEntity type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoomTypeEntity getType() {
        return type;
    }

    public void setType(RoomTypeEntity typeId) {
        this.type = typeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomEntity that = (RoomEntity) o;
        return id == that.id && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
