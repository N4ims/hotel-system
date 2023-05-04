package com.n4ims.hotelsystem.persistence;

import com.n4ims.hotelsystem.entities.*;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.*;

public class BookingDataServiceImpl implements BookingDataService{
    private static final String PERSISTENCE_UNIT = "HOTEL_SYSTEM";
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public List<RoomBookingEntity> getAllBookingsForPeriod(Date fromDate, Date endDate) {
        return null;
    }

    @Override
    public List<RoomEntity> getAllFreeRoomsForPeriod(RoomTypeEntity roomType, Date fromDate, Date toDate) {

        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
             EntityManager em = factory.createEntityManager()) {

            TypedQuery<RoomEntity> query = em.createQuery("""
                SELECT e FROM RoomEntity e
                    WHERE e NOT IN (SELECT DISTINCT r FROM RoomEntity r
                                 INNER JOIN e.roomBookings b
                                    WHERE r.id = b.id
                                    AND (
                                        (b.fromDate >= ?1 AND b.toDate <= ?2)
                                        OR (b.fromDate <= ?1 AND b.toDate > ?1)
                                        OR (b.fromDate >= ?1 AND b.toDate > ?1)
                                    )
                                )
                    AND ( e.type = ?3 OR ?3 = null)
            """, RoomEntity.class);

            query.setParameter(1, fromDate);
            query.setParameter(2, toDate);
            query.setParameter(3, roomType);

            try {
                log.debug("Fetching free rooms from database", fromDate, toDate);
                List<RoomEntity> freeRooms = query.getResultList();
                return freeRooms;
            } catch (NoResultException e) {
                log.info("No rooms for period " + fromDate + "-" + toDate);
                return new ArrayList<RoomEntity>();
            } catch (PersistenceException e) {
                log.error(e.toString(), e);
                throw e;
            }
        } catch (PersistenceException e){
            log.error(e.toString(), e);
            throw e;
        }
    }

    @Override
    public List<CateringTypeEntity> getAllCateringTypes() throws PersistenceException{
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
             EntityManager em = factory.createEntityManager();) {

            TypedQuery<CateringTypeEntity> query = em.createQuery("SELECT t FROM CateringTypeEntity t", CateringTypeEntity.class);

            return executeTypedQuery(query);
        } catch (NoResultException e) {
            log.info("No catering types in database");
            return new ArrayList<CateringTypeEntity>();
        } catch (PersistenceException e) {
            // TODO show user error message: database down
            log.error(e.toString(), e);
            throw e;
        }
    }

    @Override
    public List<RoomTypeEntity> getAllRoomTypes() throws PersistenceException {
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
             EntityManager em = factory.createEntityManager();) {

            TypedQuery<RoomTypeEntity> query = em.createQuery("SELECT t FROM RoomTypeEntity t", RoomTypeEntity.class);

            return executeTypedQuery(query);
        } catch (NoResultException e) {
            log.info("No room types in database", e);
            return new ArrayList<RoomTypeEntity>();
        } catch (PersistenceException e) {
            // TODO show user error message: database down
            log.error(e.toString(), e);
            throw e;
        }
    }

    private <T> List<T> executeTypedQuery(TypedQuery<T> query) {
        try {
            List<T> queryResultList = query.getResultList();

            return queryResultList;
        } catch (NoResultException e) {
            log.error(e.toString(), e);
            return new ArrayList<T>();
        } catch (PersistenceException e) {
            // TODO show user error message: database down
            log.error(e.toString(), e);
            throw e;
        }
    }

    private <T> void persistSingleEntity(T entity) throws PersistenceException{
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = factory.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (PersistenceException e){
            em.getTransaction().rollback();
            log.error(e.toString(), e);
            throw e;
        } finally {
            factory.close();
            em.close();
        }
    }

    @Override
    public void persistRoomBooking(RoomBookingEntity roomBooking) throws PersistenceException {
        persistSingleEntity(roomBooking);
    }

    @Override
    public void persistAddress(AddressEntity address) throws PersistenceException{
        persistSingleEntity(address);
    }

    @Override
    public void persistGuest(GuestEntity guest) throws PersistenceException{
        persistSingleEntity(guest);
    }

    @Override
    public void persistCateringBookings(Set<CateringBookingEntity> cateringBookings) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = factory.createEntityManager();

        try{
            em.getTransaction().begin();

            for(CateringBookingEntity cb: cateringBookings){
                em.persist(cb);
            }

            em.getTransaction().commit();
        } catch (PersistenceException e){
            em.getTransaction().rollback();
            log.error(e.toString(), e);
            throw e;
        } finally {
            factory.close();
            em.close();
        }
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
