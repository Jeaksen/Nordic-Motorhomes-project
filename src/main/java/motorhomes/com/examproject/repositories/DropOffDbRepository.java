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
 * todo check if parameter (int) 'reservationId' is needed somewhere else than create method
 */
public class DropOffDbRepository {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;

    public DropOffDbRepository() throws SQLException{
        this.connection = DbConnection.getConnection();
    }

    //Prawdopodobnie nie potrzebujemy tego, wiec w pickups nie musisz tego implementowac
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

    //todo check if this method is written correctly
    public boolean create(DropOff dropOff, int reservationId) throws SQLException {

        statement = connection.prepareStatement("INSERT INTO dropoffs(dropoff_location, dropoff_distance, reservation_id) VALUES (?, ?, ?)");
        statement.setString(1, dropOff.getDropOffLocation());
        statement.setInt(2, dropOff.getDropOffDistance());
        statement.setInt(3, reservationId);
        boolean creationSuccessful = statement.execute();
        statement = null;
        return creationSuccessful;
    }

    //todo reservationID zamiast dropOffId, bo dropOff jest przypisany do rezerwacji, wiec bedziemy go szukac po jej ID
    public DropOff read(int dropoffId) throws SQLException {

        statement = connection.prepareStatement("SELECT * FROM dropoffs WHERE dropoff_id=?");
        statement.setInt(1, dropoffId);
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
