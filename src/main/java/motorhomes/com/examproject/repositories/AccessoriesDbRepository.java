package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.Accessory;
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
 * todo comments
 */
@Repository
public class AccessoriesDbRepository implements ICrudRepository<Accessory>{

    private PreparedStatement statement;
    private ResultSet result;
    private DBConnector connector;

    @Autowired
    public void setConnector(DBConnector connector) {
        System.out.println("REPAIRS: OK");
        this.connector = connector;
    }

    public AccessoriesDbRepository() {
    }

    @Override
    public ArrayList<Accessory> readAll() throws SQLException {
        ArrayList<Accessory> accessories = new ArrayList<>();

        statement = connector.getConnection().prepareStatement("SELECT  * FROM accessories");
        result = statement.executeQuery();
        while (result.next()){
            accessories.add(new Accessory(result.getInt("accessory_id"), result.getString("name"), result.getInt("price")));
        }

        statement = null;
        result = null;
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
        statement = connector.getConnection().prepareStatement("INSERT  INTO accessories(name, price) VALUES (?,?)");
        statement.setString(1, accessory.getName());
        statement.setInt(2, accessory.getPrice());
        boolean creationSuccessful = statement.execute();
        statement = null;
        return creationSuccessful;
    }

    @Override
    public Accessory read(int accessoryId) throws SQLException {

        statement = connector.getConnection().prepareStatement("SELECT * FROM accessories WHERE accessory_id = ?");
        statement.setInt(1, accessoryId);
        result = statement.executeQuery();
        Accessory accessory = null;

        if (result.next()){
            accessory = new Accessory(result.getInt("accessory_id"), result.getString("name"), result.getInt("price"));
        }
        statement = null;
        result = null;
        return accessory;
    }

    @Override
    public void update(Accessory accessory) throws SQLException {

        statement = connector.getConnection().prepareStatement("UPDATE accessories SET name=?, price=? WHERE accessory_id=?");
        statement.setString(1, accessory.getName());
        statement.setInt(2, accessory.getPrice());
        statement.setInt(3, accessory.getId());
        statement.execute();
        statement = null;
    }

    @Override
    public void delete(int accessoryId) throws SQLException {

        statement = connector.getConnection().prepareStatement("DELETE FROM accessories WHERE accessory_id=? ");
        statement.setInt(1, accessoryId);
        statement.execute();
        statement = null;
    }
}
