package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.Repair;
import motorhomes.com.examproject.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @ Alicja Drankowska
 * todo comments
 */
public class RepairsDbRepository implements ICrudRepository<Repair> {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;

    public RepairsDbRepository() throws SQLException{
        this.connection = DbConnection.getConnection();
    }

    @Override
    public ArrayList<Repair> readAll() throws SQLException {

        ArrayList<Repair> repairs = new ArrayList<>();
        statement = connection.prepareStatement("SELECT * FROM repairs");
        result = statement.executeQuery();
        Repair repair = null;

        while (result.next()){
            repair = new Repair(result.getInt("repair_id"), result.getString("problem"), result.getInt("motorhome_id"), result.getString("repair_status"));
        }
        statement = null;
        result = null;
        return repairs;
    }

    @Override
    public boolean create(Repair repair) throws SQLException {

        System.out.println(repair);
        statement = connection.prepareStatement("INSERT INTO repairs(problem, repair_status, motorhome_id) VALUES (?,?,?)");
        statement.setString(1, repair.getProblem());
        statement.setString(2, repair.getRepairStatus());
        statement.setInt(3, repair.getMotorhomeId());
        boolean creationSuccessful = statement.execute();
        statement = null;
        return creationSuccessful;
    }

    @Override
    public Repair read(int repairId) throws SQLException {

        statement = connection.prepareStatement("SELECT * FROM repairs WHERE repair_id=?");
        statement.setInt(1, repairId);
        result = statement.executeQuery();
        Repair repair = null;

        if (result.next()){
            repair = new Repair(result.getInt("repair_id"), result.getString("problem"), result.getInt("motorhomeId"), result.getString("repair_status"));
        }
        statement = null;
        result = null;
        return repair;
    }

    @Override
    public void update(Repair repair) throws SQLException {

        statement = connection.prepareStatement("UPDATE repairs SET problem, repair_status, motorhome_id WHERE repair_id=?");
        statement.setString(1, repair.getProblem());
        statement.setString(2, repair.getRepairStatus());
        statement.setInt(3, repair.getMotorhomeId());
        statement.setInt(4, repair.getRepairId());
        statement.execute();
        statement = null;
    }

    @Override
    public void delete(int repairId) throws SQLException {

        statement = connection.prepareStatement("DELETE FROM repairs WHERE repair_id=?");
        statement.setInt(1, repairId);
        statement.execute();
        statement = null;
    }
}
