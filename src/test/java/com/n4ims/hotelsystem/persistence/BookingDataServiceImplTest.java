package com.n4ims.hotelsystem.persistence;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.n4ims.hotelsystem.entities.AddressEntity;
import com.n4ims.hotelsystem.entities.CateringBookingEntity;
import com.n4ims.hotelsystem.entities.GuestEntity;
import com.n4ims.hotelsystem.entities.RoomTypeEntity;
import jakarta.persistence.*;
import org.junit.Before;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import utils.AnyContainer;


import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.TypedQuery;



public class BookingDataServiceImplTest {

    private BookingDataServiceImpl bookingDataServiceImpl;
    private EntityManagerFactory factory;
    private EntityManager em;
    private TypedQuery<GuestEntity> query;

    @BeforeEach
    public void setUp() {
        em = mock(EntityManager.class);
        bookingDataServiceImpl = new BookingDataServiceImpl(em);
        query = mock(TypedQuery.class);

//        when(Persistence.createEntityManagerFactory(any())).thenReturn(factory);
//        when(factory.createEntityManager()).thenReturn(em);
        when(em.createQuery(anyString(), eq(GuestEntity.class))).thenReturn(query);
    }


    @Test
    public void persistCateringBookingsTest() {

        Set<CateringBookingEntity> emptyEntities = Set.of(
            new CateringBookingEntity(null, null, null, new Date(2023)),
            new CateringBookingEntity(null, null, null, new Date(2022))
        );

        EntityTransaction transaction = Mockito.mock(EntityTransaction.class);
        Mockito.doNothing().when(transaction).begin();
        Mockito.doNothing().when(transaction).commit();

        when(em.getTransaction()).thenReturn(transaction);

        // CateringBookingEntity entity = new CateringBookingEntity();

        // Give back Empty ArrayList<GuestEntity>

        bookingDataServiceImpl.persistCateringBookings(emptyEntities);

        InOrder executionOrder = Mockito.inOrder(transaction, em);
        executionOrder.verify(transaction).begin();
        executionOrder.verify(em, times(emptyEntities.size())).persist(Mockito.<CateringBookingEntity>any());
        executionOrder.verify(transaction).commit();


        // 2. Testcase: Guest in database
//        ArrayList<GuestEntity> guestList =
//        when(query.getResultList()).thenReturn(new ArrayList<>());

    }

    @Test
    public void ifGuestInDbUpdateIdTest() {

        // Guest without ID
        List<GuestEntity> paramGuests = new ArrayList<>();
        GuestEntity paramGuest = new GuestEntity();
        paramGuest.setFirstName("Lennox");
        paramGuest.setLastName("Schemen");
        paramGuest.setBirthdate(Date.valueOf("2000-12-12"));
        paramGuest.setTelephoneNumber("+491010");
        paramGuest.setEmailAddress("a@b.c");

        paramGuests.add(paramGuest);

        // 1. Testcase: Guest is not in database

//        when(Persistence.createEntityManagerFactory(any())).thenReturn(factory);
        //when(factory.createEntityManager()).thenReturn(em);

        // when(em.createQuery(anyString(), GuestEntity.class)).thenReturn(query);

        EntityManager em = mock(EntityManager.class);

//        TypedQuery<AddressEntity> query = EM.createQuery("""
//                        SELECT a FROM AddressEntity a
//                        WHERE a.streetName = :streetName
//                            AND a.streetNumber = :streetNr
//                            AND a.place = :place
//                            AND a.postcode = :postcode
//                            AND a.country = :country
//                        """, AddressEntity.class);
        doNothing().when(query).setParameter(anyString(), any());
        // when(em.createQuery(anyString(), eq(GuestEntity.class))).thenReturn(query);
        // when(query.setParameter(anyString(), any())).thenReturn(query);
        // when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(bookingDataServiceImpl.executeTypedQuery(Mockito.<TypedQuery<GuestEntity>>any())).thenReturn(new ArrayList<>());

        when(em.createQuery(anyString(), GuestEntity.class)).thenReturn(query);

        assertFalse(bookingDataServiceImpl.ifGuestInDbUpdateId(paramGuest));
        // ID should still be null
        assertNull(paramGuest.getId());

//
//        // 2. Testcase: Guest is in database and ID gets updated
//
//        List<GuestEntity> dbGuests = new ArrayList<GuestEntity>();
//        GuestEntity existingGuest = new GuestEntity();
//        existingGuest.setId(1);
//        existingGuest.setFirstName("Lennox");
//        existingGuest.setLastName("Schemen");
//        existingGuest.setBirthdate(Date.valueOf("2000-12-12"));
//        existingGuest.setTelephoneNumber("+491010");
//        existingGuest.setEmailAddress("a@b.c");
//
//        dbGuests.add(existingGuest);
//
//        doReturn(dbGuests).when(bookingDataServiceImpl.executeTypedQuery(any()));
////        when(bookingDataServiceImpl.executeTypedQuery(any())).thenReturn(dbGuests);
//
//        assertTrue(bookingDataServiceImpl.ifGuestInDbUpdateId(paramGuest));
//        assertEquals(existingGuest.getId(), paramGuest.getId());
//
//
//        // 3. Testcase: Guest in DB is the same guest as the paramGuest, but has different ID
//        paramGuest.setId(2);
//        when(em.find(GuestEntity.class, paramGuest.getId())).thenReturn(existingGuest);
//
//        assertTrue(bookingDataServiceImpl.ifGuestInDbUpdateId(paramGuest));
//        assertEquals(existingGuest.getId(), paramGuest.getId());
//

    }

}
