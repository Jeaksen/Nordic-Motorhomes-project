package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.Accessory;
import motorhomes.com.examproject.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Alicja Drankowska
 * todo comments
 */
public class AccessoriesDbRepository implements ICrudRepository<Accessory>{

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public AccessoriesDbRepository() throws SQLException {
        this.connection = DbConnection.getConnection();
    }

    @Override
    public ArrayList<Accessory> readAll() throws SQLException {
        ArrayList<Accessory> accessories = new ArrayList<>();

        preparedStatement = connection.prepareStatement("SELECT  * FROM accessories");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            accessories.add(new Accessory(resultSet.getInt("accessory_id"), resultSet.getString("name"), resultSet.getInt("price")));
        }

        preparedStatement = null;
        resultSet = null;
        return accessories;
    }

    public ArrayList<Accessory> readAll(List<Integer> accessoryIds) throws SQLException {
        ArrayList<Accessory> accessories = new ArrayList<>();

        for (int accessoryId: accessoryIds) {
            accessories.add(read(accessoryId));
        }

        return accessories;
    }

    @Override
    public boolean create(Accessory accessory) throws SQLException {

        System.out.println(accessory);
        preparedStatement = connection.prepareStatement("INSERT  INTO accessories(name, price) VALUES (?,?)");
        preparedStatement.setString(1, accessory.getName());
        preparedStatement.setInt(2, accessory.getPrice());
        boolean creationSuccessful = preparedStatement.execute();
        preparedStatement = null;
        return creationSuccessful;
    }

    @Override
    public Accessory read(int accessoryId) throws SQLException {

        preparedStatement = connection.prepareStatement("SELECT * FROM accessories WHERE accessory_id = ?");
        preparedStatement.setInt(1, accessoryId);
        resultSet = preparedStatement.executeQuery();
        Accessory accessory = null;

        if (resultSet.next()){
            accessory = new Accessory(resultSet.getInt("accessory_id"), resultSet.getString("name"), resultSet.getInt("price"));
        }
        preparedStatement = null;
        resultSet = null;
        return accessory;
    }

    @Override
    public void update(Accessory accessory) throws SQLException {

        preparedStatement = connection.prepareStatement("UPDATE accessories SET name=?, price=? WHERE accessory_id=?");
        preparedStatement.setString(1, accessory.getName());
        preparedStatement.setInt(2, accessory.getPrice());
        preparedStatement.setInt(3, accessory.getId());
        preparedStatement.execute();
        preparedStatement = null;
    }

    @Override
    public void delete(int accessoryId) throws SQLException {

        preparedStatement = connection.prepareStatement("DELETE FROM accessories WHERE accessory_id=? ");
        preparedStatement.setInt(1, accessoryId);
        preparedStatement.execute();
        preparedStatement = null;
    }
}
