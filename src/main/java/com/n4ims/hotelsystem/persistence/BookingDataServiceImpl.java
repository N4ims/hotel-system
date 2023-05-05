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
        boolean inDb = ifAddressInDbUpdateId(address);

        persistAddress(address);
    }

    @Override
    public void persistGuest(GuestEntity guest) throws PersistenceException{
        boolean inDb = ifGuestInDbUpdateId(guest);

        persistGuest(guest);
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

    public boolean ifAddressInDbUpdateId(AddressEntity address){
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
             EntityManager em = factory.createEntityManager();) {


            //check if entity already exists in DB
            if (address.getId() == null){
                TypedQuery<AddressEntity> query = em.createQuery("""
                        SELECT a FROM AddressEntity a 
                        WHERE a.streetName = :streetName
                            AND a.streetNumber = :streetNr
                            AND a.place = :place
                            AND a.postcode = :postcode
                            AND a.country = :country
                        """, AddressEntity.class);

                query.setParameter("streetName", address.getStreetName());
                query.setParameter("streetNr", address.getStreetNumber());
                query.setParameter("place", address.getPlace());
                query.setParameter("postcode", address.getPostcode());
                query.setParameter("country", address.getCountry());

                List<AddressEntity> guests = executeTypedQuery(query);

                if (guests.isEmpty()){
                    return false;
                } else{
                    Integer id = guests.get(0).getId();
                    address.setId(id);
                    return true;
                }
            } else {
                AddressEntity g = em.find(AddressEntity.class, address.getId());
                address.setId(g.getId());
                return true;
            }
        } catch (NoResultException e) {
            log.info("No catering types in database");
            return false;
        } catch (PersistenceException e) {
            // TODO show user error message: database down
            log.error(e.toString(), e);
            throw e;
        }

    }

    private boolean ifGuestInDbUpdateId(GuestEntity guest){
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
             EntityManager em = factory.createEntityManager();) {


            //check if entity already exists in DB
            if (guest.getId() == null){
                TypedQuery<GuestEntity> query = em.createQuery("""
                        SELECT g FROM GuestEntity g 
                        WHERE g.firstName = :firstName
                            AND g.lastName = :lastName 
                            AND g.birthDate = :birthDate
                            AND (g.telephoneNumber = :telNr OR g.email = :email)
                        """, GuestEntity.class);

                query.setParameter("firstName", guest.getFirstName());
                query.setParameter("lastName", guest.getLastName());
                query.setParameter("birthDate", guest.getBirthdate());
                query.setParameter("telNr", guest.getTelephoneNumber());
                query.setParameter("email", guest.getEmailAddress());

                List<GuestEntity> guests = executeTypedQuery(query);

                if (guests.isEmpty()){
                    return false;
                } else{
                    Integer id = guests.get(0).getId();
                    guest.setId(id);
                    return true;
                }
            } else {
                GuestEntity g = em.find(GuestEntity.class, guest.getId());
                guest.setId(g.getId());
                return true;
            }
        } catch (NoResultException e) {
            log.info("No catering types in database");
            return false;
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
