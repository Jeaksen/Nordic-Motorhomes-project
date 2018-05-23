package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.PickUp;
import motorhomes.com.examproject.util.DBConnector;
import motorhomes.com.examproject.util.DbConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @ Alicja Drankowska
 *  * This class is used to manipulate data in the database storing pick-ups
 */
@Repository
public class PickupDbRepository {

    private PreparedStatement statement;
    private ResultSet result;
    private DBConnector connector;


    public PickupDbRepository(){
    }

    @Autowired
    public void setConnector(DBConnector connector) {
        System.out.println("REPAIRS: OK");
        this.connector = connector;
    }

    /**
     * @param pickUp PickUp instance with pickUpDistance and pickUpLocation
     * @param reservationId reservation to which the pick-up is related
     */
    public void create(PickUp pickUp, int reservationId) throws SQLException{

        statement = connector.getConnection().prepareStatement("INSERT INTO pickups (pickup_location, pickup_distance, resrervation_id) VALUES (?, ?, ?)");
        statement.setString(1, pickUp.getPickUpLocation());
        statement.setInt(2, pickUp.getPickUpDistance());
        statement.setInt(3, reservationId);
        statement.execute();
        statement = null;

    }

    /**
     * This method returns pick-up data saved in the database related to the reservation ID
     * @param reservationId reservation for which pick-up should be returned
     * @return PickUp instance with distance and location, or null if no row was found
     */
    public PickUp read(int reservationId) throws SQLException{

        statement = connector.getConnection().prepareStatement("SELECT * FROM pickups WHERE resrervation_id = ?");
        statement.setInt(1, reservationId);
        result = statement.executeQuery();
        PickUp pickUp = null;

        while (result.next()){
            pickUp = new PickUp(result.getInt("pickup_id"), result.getString("pickup_location"), result.getInt("pickup_distance"));
        }

        statement = null;
        result = null;
        return pickUp;
    }

    /**
     * This method updates pickUp location and distance
     * @param pickUp pickUp object with ID, location and distance
     */
    public void update (PickUp pickUp) throws SQLException{

        statement = connector.getConnection().prepareStatement("UPDATE pickups SET pickup_location=?, pickup_distance=? WHERE pickup_id=?");
        statement.setString(1, pickUp.getPickUpLocation());
        statement.setInt(2, pickUp.getPickUpDistance());
        statement.setInt(3, pickUp.getPickUpId());
        statement.execute();
        statement = null;
    }

    /**
     * This method deletes a row in the database with given PickUp ID
     * @param pickupId ID of pickup
     */
    public void delete (int pickupId) throws SQLException{

        statement = connector.getConnection().prepareStatement("DELETE FROM pickups WHERE pickup_id=?");
        statement.setInt(1, pickupId);
        statement.execute();
        statement = null;
    }

    /**
     * This method deletes a row in the database with given reservation ID
     * @param reservationId ID of reservation related to the PickUp
     */
    public void deletePickup (int reservationId) throws SQLException {
        statement = connector.getConnection().prepareStatement("DELETE FROM pickups WHERE resrervation_id=?");
        statement.setInt(1, reservationId);
        statement.execute();
        statement = null;
    }

}
