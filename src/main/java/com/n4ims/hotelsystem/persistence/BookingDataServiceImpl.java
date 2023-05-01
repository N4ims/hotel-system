package com.n4ims.hotelsystem.persistence;

import com.n4ims.hotelsystem.entities.AddressEntity;
import com.n4ims.hotelsystem.entities.GuestEntity;
import com.n4ims.hotelsystem.entities.RoomBookingEntity;
import com.n4ims.hotelsystem.entities.RoomEntity;
import jakarta.persistence.*;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class BookingDataServiceImpl implements BookingDataService{
    private static final String PERSISTENCE_UNIT = "HOTEL_SYSTEM";
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public List<RoomBookingEntity> getAllBookingsForPeriod(Date fromDate, Date endDate) {
        return null;
    }

    @Override
    public List<RoomEntity> getAllFreeRoomsForPeriod(Date fromDate, Date toDate) {

        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
             EntityManager em = factory.createEntityManager();) {

            TypedQuery<RoomEntity> query = em.createQuery("""
                SELECT e FROM RoomEntity e
                    WHERE e NOT IN (SELECT DISTINCT r FROM RoomEntity r
                                 INNER JOIN e.roomBookings b
                                    WHERE r.id = b.id
                                    AND (b.fromDate >= ?1 AND b.toDate <= ?2)
                                    OR (b.fromDate <= ?1 AND b.toDate > ?1)
                                    OR (b.fromDate >= ?1 AND b.toDate > ?1)
                    )
            """, RoomEntity.class);

            query.setParameter(1, fromDate);
            query.setParameter(2, toDate);

            try {
                List<RoomEntity> freeRooms = query.getResultList();

                System.out.println("fromDate: " + fromDate);
                System.out.println("toDate: " + toDate);
                System.out.println("Free rooms: " + freeRooms);
                return freeRooms;
            } catch (NoResultException e) {
                log.error(e.toString(), e);
                return new ArrayList<RoomEntity>();
            } catch (JDBCConnectionException e) {
                // TODO show user error message: database down
                log.error(e.toString(), e);
                return new ArrayList<RoomEntity>();
            }
        }
    }

    @Override
    public void createRoomBooking(RoomBookingEntity roomBooking) throws PersistenceException {
        try(EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            EntityManager em = factory.createEntityManager();)
        {
            em.persist(roomBooking);
        } catch (PersistenceException e){
            log.error(e.toString(), e);
            throw e;
        }
    }

    @Override
    public void updateRoomBooking(RoomBookingEntity booking) {

    }

    @Override
    public void createAddress(AddressEntity address) {

    }

    @Override
    public void createGuest(GuestEntity guest) {

    }

    private Properties getDbAccessProperties() {
        Properties dbAccessProperties;

        try(InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties") ) {
            dbAccessProperties = new Properties();
            dbAccessProperties.load(is);
        }
        catch(IOException | IllegalArgumentException | NullPointerException e ) {
            final String msg = "Loading database connection properties failed";
            log.error(msg, e);
            throw new RuntimeException(msg);
        }
        return dbAccessProperties;
    }
}
