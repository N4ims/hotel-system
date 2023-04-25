package com.n4ims.hotelsystem.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cleaning_types", schema = "hotel_system")
public class CleaningTypeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "description")
    private String description;

    public int getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CleaningTypeEntity that = (CleaningTypeEntity) o;
        return id == that.id && type.equals(that.type) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, description);
    }
}
