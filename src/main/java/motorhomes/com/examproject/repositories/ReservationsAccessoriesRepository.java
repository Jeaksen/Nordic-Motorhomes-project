package motorhomes.com.examproject.repositories;


import motorhomes.com.examproject.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ReservationsAccessoriesRepository {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public ReservationsAccessoriesRepository() throws SQLException {
        this.connection = DbConnection.getConnection();
    }


    /**
     * This method retrieves all accessories with their quantity for a given  reservation
     * @param reservationId ID of reservation for which accessories should be returned
     * @return Map object, with accessory ID as the key, and quantity as teh value
     * @throws SQLException if there was an error while getting the data
     */
    public Map<Integer, Integer> readAll (int reservationId) throws SQLException {
        Map<Integer, Integer> accessoryIdToQuantity = new HashMap<>();
        statement = connection.prepareStatement("SELECT * FROM `reservations-accessories` WHERE reservation_id=?;");
        statement.setInt(1, reservationId);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            accessoryIdToQuantity.put(resultSet.getInt("accessory_id"), resultSet.getInt("quantity"));
        }
        statement = null;
        resultSet = null;
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
        statement = connection.prepareStatement("SELECT * FROM `reservations-accessories` WHERE reservation_id=? AND accessory_id=?");
        statement.setInt(1, reservationId);
        statement.setInt(2, accessoryId);
        resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("quantity");
        }

        return -1;
    }

    public void create (int reservationId, int accessoryId, int quantity) throws SQLException {
        statement = connection.prepareStatement("INSERT INTO `reservations-accessories` (reservation_id, accessory_id, quantity) VALUES (?,?,?);");
        statement.setInt(1, reservationId);
        statement.setInt(2, accessoryId);
        statement.setInt(3, quantity);
        statement.execute();
    }

    public void delete (int reservationId, int accessoryId) throws SQLException {
        statement = connection.prepareStatement("DELETE FROM `reservations-accessories` WHERE reservation_id = ? AND accessory_id=?;");
        statement.setInt(1, reservationId);
        statement.setInt(2, accessoryId);
        statement.execute();
    }

    public void update (int reservationId, int accessotyId, int quantity) throws SQLException {
        statement = connection.prepareStatement("UPDATE `reservations-accessories` SET quantity=? WHERE reservation_id=? AND accessory_id=?;");
        statement.setInt(1, quantity);
        statement.setInt(2, reservationId);
        statement.setInt(3, accessotyId);
        statement.execute();
    }
}
