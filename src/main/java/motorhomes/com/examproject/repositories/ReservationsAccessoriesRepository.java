package motorhomes.com.examproject.repositories;


import motorhomes.com.examproject.util.DBConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Pawel Pohl
 * This class is used to manipulate data in the database storing quantities of certain accessories associated with a reservation
 */
@Repository
public class ReservationsAccessoriesRepository {

    private PreparedStatement statement;
    private ResultSet result;
    private DBConnector connector;
    

    public ReservationsAccessoriesRepository() {

    }

    @Autowired
    public void setConnector(DBConnector connector) {
        System.out.println("RESERVATION_ACCESSORIES: OK");
        this.connector = connector;
    }


    /**
     * This method retrieves all accessories with their quantity for a given  reservation
     * @param reservationId ID of reservation for which accessories should be returned
     * @return Map object, with accessory ID as the key, and quantity as teh value
     * @throws SQLException if there was an error while getting the data
     */
    public Map<Integer, Integer> readAll (int reservationId) throws SQLException {
        Map<Integer, Integer> accessoryIdToQuantity = new HashMap<>();
        statement = connector.getConnection().prepareStatement("SELECT * FROM `reservations-accessories` WHERE reservation_id=?;");
        statement.setInt(1, reservationId);
        result = statement.executeQuery();
        while (result.next()) {
            accessoryIdToQuantity.put(result.getInt("accessory_id"), result.getInt("quantity"));
        }
        statement = null;
        result = null;
        return accessoryIdToQuantity;
    }


    /**
     * This method seraches for quantity assigned to a given reservation and accessory ID in the database
     * @param reservationId ID of reservation that the quantity is searched for
     * @param accessoryId ID of accessory that the quantity is searched for
     * @return value of quantity column or -1 if row with such reservation and accessory id was not found
     * @throws SQLException if error occurs while getting the data
     */
    public int getQuantity (int reservationId, int accessoryId) throws SQLException {
        statement = connector.getConnection().prepareStatement("SELECT * FROM `reservations-accessories` WHERE reservation_id=? AND accessory_id=?");
        statement.setInt(1, reservationId);
        statement.setInt(2, accessoryId);
        result = statement.executeQuery();
        if (result.next()) {
            return result.getInt("quantity");
        }

        return -1;
    }

    /**
     * This method creates new row in the database with given reservationId, accessoryId and quantity
     */
    public void create (int reservationId, int accessoryId, int quantity) throws SQLException {
        statement = connector.getConnection().prepareStatement("INSERT INTO `reservations-accessories` (reservation_id, accessory_id, quantity) VALUES (?,?,?);");
        statement.setInt(1, reservationId);
        statement.setInt(2, accessoryId);
        statement.setInt(3, quantity);
        statement.execute();
    }

    /**
     * This method deletes a row in the database where reservationId and accessoryId equals given ones
     */
    public void delete (int reservationId, int accessoryId) throws SQLException {
        statement = connector.getConnection().prepareStatement("DELETE FROM `reservations-accessories` WHERE reservation_id = ? AND accessory_id=?;");
        statement.setInt(1, reservationId);
        statement.setInt(2, accessoryId);
        statement.execute();
    }

    /**
     * This method updates the quantity in a row where reservationId and accessoryId equals given ones
     * @param reservationId
     * @param accessoryId
     * @param quantity
     * @throws SQLException
     */
    public void update (int reservationId, int accessoryId, int quantity) throws SQLException {
        statement = connector.getConnection().prepareStatement("UPDATE `reservations-accessories` SET quantity=? WHERE reservation_id=? AND accessory_id=?;");
        statement.setInt(1, quantity);
        statement.setInt(2, reservationId);
        statement.setInt(3, accessoryId);
        statement.execute();
    }
}
