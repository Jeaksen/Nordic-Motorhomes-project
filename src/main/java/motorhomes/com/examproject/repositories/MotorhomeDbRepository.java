package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.Motorhome;
import motorhomes.com.examproject.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * @ Alicja Drankowska
 * todo add comments!
 */
public class MotorhomeDbRepository {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;

    private MotorhomeDescriptionDbRepository motorhomeDescriptionDbRepository;

    public MotorhomeDbRepository() throws SQLException{
        this.connection = DbConnection.getConnection();
    }

    public ArrayList<Motorhome> readAll() throws SQLException {

        ArrayList<Motorhome> motorhomes = new ArrayList<>();
        MotorhomeDescriptionDbRepository motorhomeDescriptionDbRepository = null;
        statement = connection.prepareStatement("SELECT * FROM motorhomes");
        result = statement.executeQuery();
        while (result.next()){
            motorhomes.add(new Motorhome(result.getInt("motorhome_id"), result.getString("licence_plate"), result.getString("motorhome_status"), motorhomeDescriptionDbRepository.read(result.getInt("decription_id"))));
        }
        statement = null;
        result = null;
        return motorhomes;
    }


    public boolean create(Motorhome motorhome, int motorhomeDescriptionId) throws SQLException {

        statement = connection.prepareStatement("INSERT INTO motorhomes(licence_plate, motorhome_status, description_id) VALUES (?, ?, ?)");
        statement.setString(1, motorhome.getLicencePlate());
        statement.setString(2, motorhome.getMotorhomeStatus());
        statement.setInt(3, motorhomeDescriptionId);
        boolean creationSuccessful = statement.execute();
        statement = null;
        return creationSuccessful;
    }

    public Motorhome read(int motorhomeId) throws SQLException {

        statement = connection.prepareStatement("SELECT * FROM motorhomes WHERE motorhome_id=?");
        statement.setInt(1, motorhomeId);
        result = statement.executeQuery();
        Motorhome motorhome = null;
        MotorhomeDescriptionDbRepository motorhomeDescriptionDbRepository = null;

        if (result.next()){
            motorhome = new Motorhome(result.getInt("motorhome_id"), result.getString("licence_plate"), result.getString("motorhome_status"), motorhomeDescriptionDbRepository.read(result.getInt("description_id")));
        }
        statement = null;
        result = null;
        return motorhome;
    }

    public void update(Motorhome motorhome) throws SQLException {

        statement = connection.prepareStatement("UPDATE motorhomes SET licence_plate=?, motorhome_status=? WHERE motorhome_id=?");
        statement.setString(1, motorhome.getLicencePlate());
        statement.setString(2, motorhome.getMotorhomeStatus());
        statement.setInt(3, motorhome.getMotorhomeId());
        statement.execute();
        statement = null;
    }

    public void delete(int motorhomeId) throws SQLException {

        statement = connection.prepareStatement("DELETE FROM motorhomes WHERE motorhome_id=?");
        statement.setInt(1, motorhomeId);
        statement.execute();
        statement = null;
    }
}
