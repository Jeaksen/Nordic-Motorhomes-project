package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.MotorhomeDescription;
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
 */
@Repository
public class MotorhomeDescriptionDbRepository {

    private PreparedStatement statement;
    private ResultSet result;
    private DBConnector connector;

    @Autowired
    public void setConnector(DBConnector connector) {
        System.out.println("REPAIRS: OK");
        this.connector = connector;
    }

    public MotorhomeDescriptionDbRepository(){
    }

    public ArrayList<MotorhomeDescription> readAll() throws SQLException {
        ArrayList<MotorhomeDescription> motorhomeDescriptions = new ArrayList<>();

        statement = connector.getConnection().prepareStatement("SELECT * FROM descriptions");
        result = statement.executeQuery();
        while (result.next()){
            motorhomeDescriptions.add(new MotorhomeDescription(result.getInt("description_id"),
                    result.getString("brand"), result.getString("model"),
                    result.getInt("base_price"), result.getInt("capacity")));
        }

        statement = null;
        result = null;
        return motorhomeDescriptions;
    }

    /**
     * @param motorhomeDescription MotorhomeDescription object with all data
     * @return ID of the created row (description)
     */
    public int create(MotorhomeDescription motorhomeDescription) throws SQLException {

        System.out.println(motorhomeDescription);
        statement = connector.getConnection().prepareStatement("INSERT INTO descriptions(brand, model, base_price, capacity) VALUES (?,?,?,?)");
        statement.setString(1, motorhomeDescription.getBrand());
        statement.setString(2, motorhomeDescription.getModel());
        statement.setInt(3, motorhomeDescription.getBasePrice());
        statement.setInt(4, motorhomeDescription.getCapacity());
        statement.execute();
        statement = connector.getConnection().prepareStatement("SELECT description_id FROM descriptions WHERE brand=? AND model=? AND base_price=? AND capacity=?;");
        statement.setString(1, motorhomeDescription.getBrand());
        statement.setString(2, motorhomeDescription.getModel());
        statement.setInt(3, motorhomeDescription.getBasePrice());
        statement.setInt(4, motorhomeDescription.getCapacity());
        result = statement.executeQuery();
        int id = -1;
        if (result.next()) {
            id = result.getInt("description_id");
        }
        statement = null;
        result = null;
        return id;
    }

    public MotorhomeDescription read(int motorhomeDescriptionId) throws SQLException {

        statement = connector.getConnection().prepareStatement("SELECT * FROM descriptions WHERE description_id = ?");
        statement.setInt(1, motorhomeDescriptionId);
        result = statement.executeQuery();
        MotorhomeDescription motorhomeDescription = null;

        if (result.next()){
            motorhomeDescription = new MotorhomeDescription(result.getInt("description_id"),
                    result.getString("brand"), result.getString("model"),
                    result.getInt("base_price"), result.getInt("capacity"));
        }
        statement = null;
        result = null;
        return motorhomeDescription;
    }

    public void update(MotorhomeDescription motorhomeDescription) throws SQLException {

        statement = connector.getConnection().prepareStatement("UPDATE descriptions SET brand=?,model=?,base_price=? WHERE description_id = ?");
        statement.setString(1, motorhomeDescription.getBrand());
        statement.setString(2, motorhomeDescription.getModel());
        statement.setInt(3, motorhomeDescription.getBasePrice());
        statement.setInt(4, motorhomeDescription.getMotorhomeDescriptionId());
        statement.execute();
        statement = null;
    }

    public void delete(int motorhomeDescriptionId) throws SQLException {

        statement = connector.getConnection().prepareStatement("DELETE FROM descriptions WHERE description_id=?");
        statement.setInt(1, motorhomeDescriptionId);
        statement.execute();
        statement = null;
    }
}
