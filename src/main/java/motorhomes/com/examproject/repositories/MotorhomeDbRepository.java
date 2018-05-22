package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.Motorhome;
import motorhomes.com.examproject.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * @ Alicja Drankowska
 */
public class MotorhomeDbRepository {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;

    private MotorhomeDescriptionDbRepository motorhomeDescriptionDbRepository;

    public MotorhomeDbRepository(MotorhomeDescriptionDbRepository motorhomeDescriptionDbRepository) throws SQLException{
        this.connection = DbConnection.getConnection();
        this.motorhomeDescriptionDbRepository = motorhomeDescriptionDbRepository;
    }

    public List<Motorhome> readAll() throws SQLException {

        ArrayList<Motorhome> motorhomes = new ArrayList<>();
        statement = connection.prepareStatement("SELECT * FROM motorhomes");
        result = statement.executeQuery();
        while (result.next()){
            motorhomes.add(new Motorhome(
                    result.getInt("motorhome_id"),
                    result.getString("licence_plate"),
                    result.getString("motorhome_status"),
                    motorhomeDescriptionDbRepository.read(result.getInt("decription_id"))));
        }
        statement = null;
        result = null;
        return motorhomes;
    }

    /**
     * This method returns a list of motorhomes with indexes from given List object
     */
    public List<Motorhome> readAll(List<Integer> motorhomeIDs) throws SQLException {

        ArrayList<Motorhome> motorhomes = new ArrayList<>();
        for (int motorhomesId: motorhomeIDs) {
            motorhomes.add(this.read(motorhomesId));
        }
        return motorhomes;
    }

    public List<Integer> readAllIDs() throws SQLException {
        List<Integer> motorhomeIDs = new ArrayList<>();
        statement = connection.prepareStatement("SELECT motorhome_id from motorhomes");
        result = statement.executeQuery();
        while (result.next()){
            motorhomeIDs.add(result.getInt("motorhome_id"));
        }
        return motorhomeIDs;
    }

    /**
     * This method creates new row in the motorhomes table with: licence_plate, motorhome_status and description_id
     * @return true when row was successfully created or false in case of error.
     */
    public boolean create(Motorhome motorhome) throws SQLException {

        statement = connection.prepareStatement("INSERT INTO motorhomes(licence_plate, motorhome_status, description_id) VALUES (?, ?, ?)");
        statement.setString(1, motorhome.getLicencePlate());
        statement.setString(2, motorhome.getMotorhomeStatus());
        statement.setInt(3, motorhome.getMotorhomeDescription().getMotorhomeDescriptionId());
        boolean creationSuccessful = statement.execute();
        statement = null;
        return creationSuccessful;
    }

    public Motorhome read(int motorhomeId) throws SQLException {

        statement = connection.prepareStatement("SELECT * FROM motorhomes WHERE motorhome_id=?");
        statement.setInt(1, motorhomeId);
        result = statement.executeQuery();
        Motorhome motorhome = null;

        if (result.next()){
            motorhome = new Motorhome(result.getInt("motorhome_id"), result.getString("licence_plate"), result.getString("motorhome_status"), motorhomeDescriptionDbRepository.read(result.getInt("description_id")));
        }
        statement = null;
        result = null;
        return motorhome;
    }

    /**
     * This method updates: licence_plate, motorhome_status and description_id
     */
    public void update(Motorhome motorhome) throws SQLException {

        statement = connection.prepareStatement("UPDATE motorhomes SET licence_plate=?, motorhome_status=?, description_id=? WHERE motorhome_id=?");
        statement.setString(1, motorhome.getLicencePlate());
        statement.setString(2, motorhome.getMotorhomeStatus());
        statement.setInt(3, motorhome.getMotorhomeDescription().getMotorhomeDescriptionId());
        statement.setInt(4, motorhome.getMotorhomeId());
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
