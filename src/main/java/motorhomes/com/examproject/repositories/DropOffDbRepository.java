package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.DropOff;
import motorhomes.com.examproject.util.DBConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Alicja Drankowska
 * This class is used to manipulate data in the database storing drop-offs
 */
@Repository
public class DropOffDbRepository {

    private PreparedStatement statement;
    private ResultSet result;
    private DBConnector connector;

    public DropOffDbRepository(){
    }

    @Autowired
    public void setConnector(DBConnector connector) {
        System.out.println("REPAIRS: OK");
        this.connector = connector;
    }

    /**
     *
     * @return List object with all DropOff object stored in the database
     */
    public List<DropOff> readAll() throws SQLException {
        ArrayList<DropOff> dropOffs = new ArrayList<>();

        statement = connector.getConnection().prepareStatement("SELECT * FROM dropoffs");
        result = statement.executeQuery();
        while (result.next()){
            dropOffs.add(new DropOff(result.getInt("dropoff_id"), result.getString("dropoff_location"), result.getInt("dropoff_distance")));
        }

        statement = null;
        result = null;
        return dropOffs;
    }

    /**
     * @param dropOff DropOff instance with distance and location
     * @param reservationId ID of reservation for this drop-off
     */
    public void create(DropOff dropOff, int reservationId) throws SQLException {
        statement = connector.getConnection().prepareStatement("INSERT INTO dropoffs(dropoff_location, dropoff_distance, reservation_id) VALUES (?, ?, ?)");
        statement.setString(1, dropOff.getDropOffLocation());
        statement.setInt(2, dropOff.getDropOffDistance());
        statement.setInt(3, reservationId);
        statement.execute();
        statement = null;

    }

    public DropOff read(int reservationId) throws SQLException {

        statement = connector.getConnection().prepareStatement("SELECT * FROM dropoffs WHERE reservation_id=?");
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

        statement = connector.getConnection().prepareStatement("UPDATE dropoffs SET dropoff_location=?, dropoff_distance=? WHERE dropoff_id = ?");
        statement.setString(1, dropOff.getDropOffLocation());
        statement.setInt(2, dropOff.getDropOffDistance());
        statement.setInt(3, dropOff.getDropOffId());
        statement.execute();
        statement = null;
    }

    public void delete(int dropoffId) throws SQLException {

        statement = connector.getConnection().prepareStatement("DELETE FROM dropoffs WHERE dropoff_id = ?");
        statement.setInt(1, dropoffId);
        statement.execute();
        statement = null;
    }

}
