package com.n4ims.hotelsystem.persistence;

import com.n4ims.hotelsystem.entities.AddressEntity;
import com.n4ims.hotelsystem.entities.GuestEntity;
import com.n4ims.hotelsystem.entities.RoomBookingEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.print.Book;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

public class BookingDataServiceImpl implements BookingDataService{
    private static final String PERSISTENCE_UNIT = "hotel_system";
    private final EntityManager entityManager;
    private static final Logger log = LoggerFactory.getLogger( MethodHandles.lookup().lookupClass() );

    public BookingDataServiceImpl(){
        entityManager = getEntityManager();
    }
    @Override
    public List<RoomBookingEntity> getAllBookingsForPeriod(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public List<RoomBookingEntity> getAllFreeRoomsForPeriod(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public void createRoomBooking(RoomBookingEntity roomBooking) throws PersistenceException {
        try{
            entityManager.persist(roomBooking);
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

    // private EntityManager getEntityManager(){}

    private EntityManager getEntityManager() throws PersistenceException {
        EntityManager em;

        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            em = factory.createEntityManager();
        } catch ( PersistenceException e) {
            log.error(e.toString(), e);
            throw e;
        }

        return em;
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
