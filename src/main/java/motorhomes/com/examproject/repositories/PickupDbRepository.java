package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.PickUp;
import motorhomes.com.examproject.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ Alicja Drankowska
 * todo comments
 */
public class PickupDbRepository {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;

    public PickupDbRepository() throws SQLException{
        this.connection = DbConnection.getConnection();
    }

    public boolean create(PickUp pickUp, int reservationId) throws SQLException{

        statement = connection.prepareStatement("INSERT INTO pickups (pickup_location, pickup_distance, resrervation_id) VALUES (?, ?, ?)");
        statement.setString(1, pickUp.getPickUpLocation());
        statement.setInt(2, pickUp.getPickUpDistance());
        statement.setInt(3, reservationId);
        boolean creationSuccessful = statement.execute();
        statement = null;
        return creationSuccessful;
    }

    public PickUp read(int reservationId) throws SQLException{

        statement = connection.prepareStatement("SELECT * FROM pickups WHERE resrervation_id = ?");
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

    public void update (PickUp pickUp) throws SQLException{

        statement = connection.prepareStatement("UPDATE pickups SET pickup_location=?, pickup_distance=? WHERE pickup_id=?");
        statement.setString(1, pickUp.getPickUpLocation());
        statement.setInt(2, pickUp.getPickUpDistance());
        statement.setInt(3, pickUp.getPickUpId());
        statement.execute();
        statement = null;
    }

    public void delete (int pickupId) throws SQLException{

        statement = connection.prepareStatement("DELETE FROM pickups WHERE pickup_id=?");
        statement.setInt(1, pickupId);
        statement.execute();
        statement = null;
    }

}
