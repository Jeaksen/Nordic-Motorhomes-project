package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.DropOff;
import motorhomes.com.examproject.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * @ Alicja Drankowska
 * todo comments
 */
public class DropOffDbRepository {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;

    public DropOffDbRepository() throws SQLException{
        this.connection = DbConnection.getConnection();
    }

    //Probably won't be used
    public ArrayList<DropOff> readAll() throws SQLException {
        ArrayList<DropOff> dropOffs = new ArrayList<>();

        statement = connection.prepareStatement("SELECT * FROM dropoffs");
        result = statement.executeQuery();
        while (result.next()){
            dropOffs.add(new DropOff(result.getInt("dropoff_id"), result.getString("dropoff_location"), result.getInt("dropoff_distance")));
        }

        statement = null;
        result = null;
        return dropOffs;
    }

    public boolean create(DropOff dropOff, int reservationId) throws SQLException {

        statement = connection.prepareStatement("INSERT INTO dropoffs(dropoff_location, dropoff_distance, reservation_id) VALUES (?, ?, ?)");
        statement.setString(1, dropOff.getDropOffLocation());
        statement.setInt(2, dropOff.getDropOffDistance());
        statement.setInt(3, reservationId);
        boolean creationSuccessful = statement.execute();
        statement = null;
        return creationSuccessful;
    }

    public DropOff read(int reservationId) throws SQLException {

        statement = connection.prepareStatement("SELECT * FROM dropoffs WHERE dropoff_id=?");
        statement.setInt(1, reservationId);
        result = statement.executeQuery();
        DropOff dropOff = null;

        if (result.next()){
            dropOff = new DropOff(result.getInt("dropoff_id"), result.getString("dropoff_location"), result.getInt("dropoff_distance"));
        }
        statement = null;
        result = null;
        return dropOff;
    }

    public void update(DropOff dropOff) throws SQLException {

        statement = connection.prepareStatement("UPDATE dropoffs SET dropoff_location=?, dropoff_distance=? WHERE dropoff_id = ?");
        statement.setString(1, dropOff.getDropOffLocation());
        statement.setInt(2, dropOff.getDropOffDistance());
        statement.setInt(3, dropOff.getDropOffId());
        statement.execute();
        statement = null;
    }

    public void delete(int dropoffId) throws SQLException {

        statement = connection.prepareStatement("DELETE FROM dropoffs WHERE dropoff_id = ?");
        statement.setInt(1, dropoffId);
        statement.execute();
        statement = null;
    }

}
