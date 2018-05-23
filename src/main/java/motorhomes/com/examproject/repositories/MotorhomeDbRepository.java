package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.Motorhome;
import motorhomes.com.examproject.util.DBConnector;
import motorhomes.com.examproject.util.DbConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * @ Alicja Drankowska
 */
@Repository
public class MotorhomeDbRepository {

    private PreparedStatement statement;
    private ResultSet result;
    private DBConnector connector;
    private MotorhomeDescriptionDbRepository motorhomeDescriptionDbRepository;

    public MotorhomeDbRepository(MotorhomeDescriptionDbRepository motorhomeDescriptionDbRepository) {
        this.motorhomeDescriptionDbRepository = motorhomeDescriptionDbRepository;
    }

    @Autowired
    public void setConnector(DBConnector connector) {
        System.out.println("REPAIRS: OK");
        this.connector = connector;
    }

    public List<Motorhome> readAll() throws SQLException {

        ArrayList<Motorhome> motorhomes = new ArrayList<>();
        statement = connector.getConnection().prepareStatement("SELECT * FROM motorhomes");
        result = statement.executeQuery();
        while (result.next()){
            motorhomes.add(new Motorhome(
                    result.getInt("motorhome_id"),
                    result.getString("licence_plate"),
                    result.getString("motorhome_status"),
                    motorhomeDescriptionDbRepository.read(result.getInt("description_id"))));
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
        statement = connector.getConnection().prepareStatement("SELECT motorhome_id from motorhomes");
        result = statement.executeQuery();
        while (result.next()){
            motorhomeIDs.add(result.getInt("motorhome_id"));
        }
        return motorhomeIDs;
    }

    /**
     * This method creates new row in the motorhomes table with: licence_plate, motorhome_status and description_id
     */
    public void create(Motorhome motorhome) throws SQLException {
        statement = connector.getConnection().prepareStatement("INSERT INTO motorhomes(licence_plate, motorhome_status, description_id) VALUES (?, ?, ?)");
        statement.setString(1, motorhome.getLicencePlate());
        statement.setString(2, motorhome.getMotorhomeStatus());
        statement.setInt(3, motorhome.getMotorhomeDescription().getMotorhomeDescriptionId());
        statement.execute();
        statement = null;
    }

    public Motorhome read(int motorhomeId) throws SQLException {

        statement = connector.getConnection().prepareStatement("SELECT * FROM motorhomes WHERE motorhome_id=?");
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

        statement = connector.getConnection().prepareStatement("UPDATE motorhomes SET licence_plate=?, motorhome_status=?, description_id=? WHERE motorhome_id=?");
        statement.setString(1, motorhome.getLicencePlate());
        statement.setString(2, motorhome.getMotorhomeStatus());
        statement.setInt(3, motorhome.getMotorhomeDescription().getMotorhomeDescriptionId());
        statement.setInt(4, motorhome.getMotorhomeId());
        statement.execute();
        statement = null;
    }

    public void delete(int motorhomeId) throws SQLException {

        statement = connector.getConnection().prepareStatement("DELETE FROM motorhomes WHERE motorhome_id=?");
        statement.setInt(1, motorhomeId);
        statement.execute();
        statement = null;
    }
}
