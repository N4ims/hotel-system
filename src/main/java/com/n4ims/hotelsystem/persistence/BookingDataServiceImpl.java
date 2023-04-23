package com.n4ims.hotelsystem.persistence;

import com.n4ims.hotelsystem.entities.AddressesEntity;
import com.n4ims.hotelsystem.entities.GuestsEntity;
import com.n4ims.hotelsystem.entities.RoomBookingsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

public class BookingDataServiceImpl implements BookingDataService{

    private static final Logger log = LoggerFactory.getLogger( MethodHandles.lookup().lookupClass() );

    @Override
    public List<RoomBookingsEntity> getAllBookingsForPeriod(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public List<RoomBookingsEntity> getAllFreeRoomsForPeriod(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public void createRoomBooking(RoomBookingsEntity booking) {

    }

    @Override
    public void updateRoomBooking(RoomBookingsEntity booking) {

    }

    @Override
    public void createAddress(AddressesEntity address) {

    }

    @Override
    public void createGuest(GuestsEntity guest) {

    }

    // private EntityManager getEntityManager(){}

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
