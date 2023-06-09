
package com.n4ims.hotelsystem.persistence;

import com.n4ims.hotelsystem.entities.*;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.*;


/**

 This class provides an implementation of the BookingDataService interface
 that uses Jakarta Persistence (JPA) to access the database.
 @author
 */
public class BookingDataServiceImpl implements BookingDataService{

    public static final String PERSISTENCE_UNIT = ResourcePaths.PERSISTENCE_UNIT_NAME;
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EntityManager EM;

    public BookingDataServiceImpl() {
        EM = null;
    }

    public BookingDataServiceImpl(EntityManager em){
        this.EM = em;
    }

    /**
     * This method retrieves all the RoomEntity objects of a specific type that are not booked during a specified time period
     * @param roomType the type of RoomEntity object to retrieve
     * @param fromDate the start date of the period
     * @param toDate the end date of the period
     * @return a List of RoomEntity objects that are not booked during the specified period
     */
    @Override
    public List<RoomEntity> getAllFreeRoomsForPeriod(RoomTypeEntity roomType, Date fromDate, Date toDate) {

        try {
            TypedQuery<RoomEntity> query = EM.createQuery("""
                SELECT e FROM RoomEntity e
                    WHERE e NOT IN (SELECT DISTINCT r FROM RoomEntity r
                                 INNER JOIN e.roomBookings b
                                    WHERE r.id = b.id
                                    AND (
                                        (b.fromDate >= ?1 AND b.toDate <= ?2)
                                        OR (b.fromDate <= ?1 AND b.toDate > ?1)
                                        OR ( (b.fromDate >= ?1 AND b.fromDate <= ?2) AND b.toDate > ?1)
                                    )
                                )
                    AND ( e.type = ?3 OR ?3 = null)
            """, RoomEntity.class);

            query.setParameter(1, fromDate);
            query.setParameter(2, toDate);
            query.setParameter(3, roomType);

            log.debug("Fetching free rooms from database", fromDate, toDate);
            List<RoomEntity> freeRooms = executeTypedQuery(query);
            return freeRooms;

        } catch (PersistenceException e){
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**
     * This method retrieves all the CateringTypeEntity objects in the database
     * @return a List of all the CateringTypeEntity objects in the database
     * @throws PersistenceException if an error occurs while accessing the database
     */
    @Override
    public List<CateringTypeEntity> getAllCateringTypes() throws PersistenceException{
        try {

            TypedQuery<CateringTypeEntity> query = EM.createQuery("SELECT t FROM CateringTypeEntity t", CateringTypeEntity.class);

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

    /**

     Returns a list of all room types in the database.

     @return a list of all room types in the database

     @throws PersistenceException if there is an issue with the persistence layer
     */
    @Override
    public List<RoomTypeEntity> getAllRoomTypes() throws PersistenceException {
        try {

            TypedQuery<RoomTypeEntity> query = EM.createQuery("SELECT t FROM RoomTypeEntity t", RoomTypeEntity.class);

            return executeTypedQuery(query);
        } catch (NoResultException e) {
            log.info("No room types in database", e);
            return new ArrayList<RoomTypeEntity>();
        } catch (PersistenceException e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**

     Persists a single entity into the database.

     @param entity the entity to persist

     @throws PersistenceException if an error occurs while persisting the entity
     */
    private <T> void persistSingleEntity(T entity) throws PersistenceException{

        try {
            EM.getTransaction().begin();
            EM.persist(entity);
            EM.getTransaction().commit();
        } catch (PersistenceException e){
            EM.getTransaction().rollback();
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**
     * Persists the given room booking entity to the database.
     *
     * @param roomBooking the room booking entity to persist
     * @throws PersistenceException if an error occurs while accessing the database
     */
    @Override
    public void persistRoomBooking(RoomBookingEntity roomBooking) throws PersistenceException {
        persistSingleEntity(roomBooking);
    }

    /**
     * Persists the given address entity to the database. If the address already exists in the database,
     * its ID is updated instead.
     *
     * @param address the address entity to persist
     * @throws PersistenceException if an error occurs while accessing the database
     */
    @Override
    public void persistAddress(AddressEntity address) throws PersistenceException{
        ifAddressInDbUpdateId(address);

        persistSingleEntity(address);
    }

    /**
     * Persists the given guest entity to the database. If the guest already exists in the database,
     * its ID is updated instead.
     *
     * @param guest the guest entity to persist
     * @throws PersistenceException if an error occurs while accessing the database
     */
    @Override
    public void persistGuest(GuestEntity guest) throws PersistenceException{
        ifGuestInDbUpdateId(guest);

        persistSingleEntity(guest);
    }

    /**
     * Stores all CateringBookingEntities of the given Set in the Database
     * @param cateringBookings Set of CateringBookingEntities that shall be stored in the database
     */
    @Override
    public void persistCateringBookings(Set<CateringBookingEntity> cateringBookings) {

        try{
            EM.getTransaction().begin();

            for(CateringBookingEntity cb: cateringBookings){
                persistSingleEntity(cb);
            }

            EM.getTransaction().commit();
        } catch (PersistenceException e){
            EM.getTransaction().rollback();
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**
     *Checks if an AddressEntity already exists in the database and updates its id accordingly.
     *If the entity already exists in the database, its id is updated to match the existing entity's id.
     *If it does not exist, the method returns false and the entity's id remains null.
     *@param address the AddressEntity to be checked and updated
     *@return true if the AddressEntity already exists in the database and its id has been updated,
     *false if it does not exist in the database and its id remains null.
     *@throws PersistenceException if there is an error accessing the database
   */
    public boolean ifAddressInDbUpdateId(AddressEntity address){
        try {
            //check if entity already exists in DB
            if (address.getId() == null){
                TypedQuery<AddressEntity> query = EM.createQuery("""
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
        } catch (NoResultException e) {
            log.info("No catering types in database");
            return false;
        } catch (PersistenceException e) {
            log.error(e.toString(), e);
            throw e;
        }

    }

    /**

     Checks if a guest entity already exists in the database and updates its ID if it does.

     If the entity doesn't exist, it returns false.

     @param guest The guest entity to be checked and updated if necessary.

     @return True if the guest entity was found and its ID was updated, false otherwise.

     @throws PersistenceException If there was an error accessing the database.
     */
    boolean ifGuestInDbUpdateId(GuestEntity guest){
        try {
            //check if entity already exists in DB
            TypedQuery<GuestEntity> query = em.createQuery("""
                    SELECT g FROM GuestEntity g 
                    WHERE g.firstName = :firstName
                        AND g.lastName = :lastName 
                        AND g.birthdate = :birthdate
                        AND (g.telephoneNumber = :telNr OR g.emailAddress = :email)
                    """, GuestEntity.class);

            query.setParameter("firstName", guest.getFirstName());
            query.setParameter("lastName", guest.getLastName());
            query.setParameter("birthdate", guest.getBirthdate());
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
        } catch (NoResultException e) {
            log.info("No catering types in database");
            return false;
        } catch (PersistenceException e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    /**

     Executes a JPA typed query and returns the result as a List.

     @param query the JPA typed query to execute.

     @param <T> the generic type of the query result.

     @return a List of the query result objects.

     @throws PersistenceException if there is an error executing the query.
     */
    private <T> List<T> executeTypedQuery(TypedQuery<T> query) {
        try {
            List<T> queryResultList = query.getResultList();

            return queryResultList;
        } catch (NoResultException e) {
            log.error(e.toString(), e);
            return new ArrayList<T>();
        } catch (PersistenceException e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

}
