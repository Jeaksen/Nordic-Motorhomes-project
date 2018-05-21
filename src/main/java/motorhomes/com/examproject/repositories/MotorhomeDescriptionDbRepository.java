package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.MotorhomeDescription;
import motorhomes.com.examproject.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @ Alicja Drankowska
 */
public class MotorhomeDescriptionDbRepository implements ICrudRepository<MotorhomeDescription> {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public MotorhomeDescriptionDbRepository() throws SQLException{
        this.connection = DbConnection.getConnection();
    }

    //Probably won't be used
    @Override
    public ArrayList<MotorhomeDescription> readAll() throws SQLException {
        ArrayList<MotorhomeDescription> motorhomeDescriptions = new ArrayList<>();

        statement = connection.prepareStatement("SELECT * FROM descriptions");
        resultSet = statement.executeQuery();
        while (resultSet.next()){
            motorhomeDescriptions.add(new MotorhomeDescription(resultSet.getInt("description_id"),
                    resultSet.getString("brand"), resultSet.getString("model"),
                    resultSet.getInt("base_price"), resultSet.getInt("capacity")));
        }

        statement = null;
        resultSet = null;
        return motorhomeDescriptions;
    }

    @Override
    public boolean create(MotorhomeDescription motorhomeDescription) throws SQLException {

        System.out.println(motorhomeDescription);
        statement = connection.prepareStatement("INSERT INTO descriptions(brand, model, base_price) VALUES (?,?,?)");
        statement.setString(1, motorhomeDescription.getBrand());
        statement.setString(2, motorhomeDescription.getModel());
        statement.setInt(3, motorhomeDescription.getBasePrice());
        boolean creationSuccessful = statement.execute();
        statement = null;
        return creationSuccessful;
    }

    @Override
    public MotorhomeDescription read(int motorhomeDescriptionId) throws SQLException {

        statement = connection.prepareStatement("SELECT * FROM descriptions WHERE description_id = ?");
        statement.setInt(1, motorhomeDescriptionId);
        resultSet = statement.executeQuery();
        MotorhomeDescription motorhomeDescription = null;

        if (resultSet.next()){
            motorhomeDescription = new MotorhomeDescription(resultSet.getInt("description_id"),
                    resultSet.getString("brand"), resultSet.getString("model"),
                    resultSet.getInt("base_price"), resultSet.getInt("capacity"));
        }
        statement = null;
        resultSet = null;
        return motorhomeDescription;
    }

    @Override
    public void update(MotorhomeDescription motorhomeDescription) throws SQLException {

        statement = connection.prepareStatement("UPDATE descriptions SET brand=?,model=?,base_price=? WHERE description_id = ?");
        statement.setString(1, motorhomeDescription.getBrand());
        statement.setString(2, motorhomeDescription.getModel());
        statement.setInt(3, motorhomeDescription.getBasePrice());
        statement.setInt(4, motorhomeDescription.getMotorhomeDescriptionId());
        statement.execute();
        statement = null;
    }

    @Override
    public void delete(int motorhomeDescriptionId) throws SQLException {

        statement = connection.prepareStatement("DELETE FROM descriptions WHERE description_id=?");
        statement.setInt(1, motorhomeDescriptionId);
        statement.execute();
        statement = null;
    }
}
