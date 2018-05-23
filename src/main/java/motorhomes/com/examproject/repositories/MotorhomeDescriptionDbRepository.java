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
public class MotorhomeDescriptionDbRepository implements ICrudRepository<MotorhomeDescription> {

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

    //Probably won't be used
    @Override
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
//not sure if it will be needed
    public List<Integer> readAllIds() throws SQLException{
        List<Integer> motorhomeDescriptionIds = new ArrayList<>();
        statement = connector.getConnection().prepareStatement("SELECT description_id FROM descriptions");
        result = statement.executeQuery();
        while (result.next()){
            motorhomeDescriptionIds.add(result.getInt("description_id"));
        }
        return motorhomeDescriptionIds;
    }

    @Override
    public boolean create(MotorhomeDescription motorhomeDescription) throws SQLException {

        System.out.println(motorhomeDescription);
        statement = connector.getConnection().prepareStatement("INSERT INTO descriptions(brand, model, base_price) VALUES (?,?,?)");
        statement.setString(1, motorhomeDescription.getBrand());
        statement.setString(2, motorhomeDescription.getModel());
        statement.setInt(3, motorhomeDescription.getBasePrice());
        boolean creationSuccessful = statement.execute();
        statement = null;
        return creationSuccessful;
    }

    @Override
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

    @Override
    public void update(MotorhomeDescription motorhomeDescription) throws SQLException {

        statement = connector.getConnection().prepareStatement("UPDATE descriptions SET brand=?,model=?,base_price=? WHERE description_id = ?");
        statement.setString(1, motorhomeDescription.getBrand());
        statement.setString(2, motorhomeDescription.getModel());
        statement.setInt(3, motorhomeDescription.getBasePrice());
        statement.setInt(4, motorhomeDescription.getMotorhomeDescriptionId());
        statement.execute();
        statement = null;
    }

    @Override
    public void delete(int motorhomeDescriptionId) throws SQLException {

        statement = connector.getConnection().prepareStatement("DELETE FROM descriptions WHERE description_id=?");
        statement.setInt(1, motorhomeDescriptionId);
        statement.execute();
        statement = null;
    }
}
