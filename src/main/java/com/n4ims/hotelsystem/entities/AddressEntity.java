package com.n4ims.hotelsystem.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "addresses", schema = "hotel_system")
public class AddressEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "street_name")
    private String streetName;
    @Basic
    @Column(name = "street_nr")
    private String streetNr;
    @Basic
    @Column(name = "place")
    private String place;
    @Basic
    @Column(name = "postcode")
    private String postcode;
    @Basic
    @Column(name = "country")
    private String country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNr() {
        return streetNr;
    }

    public void setStreetNr(String streetNr) {
        this.streetNr = streetNr;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return id == that.id && Objects.equals(streetName, that.streetName) && Objects.equals(streetNr, that.streetNr) && Objects.equals(place, that.place) && Objects.equals(postcode, that.postcode) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, streetName, streetNr, place, postcode, country);
    }
}
