package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.Accessory;
import motorhomes.com.examproject.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccessoriesDbRepository implements ICrudRepository<Accessory>{

    ArrayList<Accessory> accessories;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public AccessoriesDbRepository() throws SQLException {
        this.connection = DbConnection.getConnection();
        this.accessories = new ArrayList<>();
    }

    @Override
    public ArrayList<Accessory> readAll() {
        accessories = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement("SELECT  * FROM accessories");
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return accessories;
    }

    @Override
    public boolean create(Accessory accessory) {
        boolean a = false;

        try {
            System.out.println(accessory);
            preparedStatement = connection.prepareStatement("INSERT  INTO accessories(name, price) VALUES (?,?)");
            preparedStatement.setString(1, accessory.getAccessoryName());
            preparedStatement.setInt(2, accessory.getAccessoryPrice());

            a = preparedStatement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return a;
    }

    @Override
    public Accessory read(int accessoryId) {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM accessories WHERE accessory_id = accessoryId");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return new Accessory(resultSet.getInt("accessory_id"), resultSet.getString("name"), resultSet.getInt("price"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return accessories.get(accessoryId);
    }

    @Override
    public void update(Accessory accessory) {
        try {
            preparedStatement = connection.prepareStatement("UPDATE accessories SET name=?, price=? WHERE accessory_id=?");
            preparedStatement.setString(1, accessory.getAccessoryName());
            preparedStatement.setInt(2, accessory.getAccessoryPrice());
            preparedStatement.setInt(3, accessory.getAccessoryId());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int accessoryId) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM accessories WHERE accessory_id=? ");
            preparedStatement.setInt(1, accessoryId);

            preparedStatement.execute();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
