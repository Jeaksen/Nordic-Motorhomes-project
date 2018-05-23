package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.Repair;
import motorhomes.com.examproject.util.DBConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @ Alicja Drankowska
 * This class is used to manipulate data in the database storing repairs
 */

@Repository
public class RepairsDbRepository{

    private PreparedStatement statement;
    private ResultSet result;
    private DBConnector connector;

    public RepairsDbRepository(){
    }

    @Autowired
    public void setConnector(DBConnector connector) {
        System.out.println("REPAIRS: OK");
        this.connector = connector;
    }


    /**
     * @return List< Repair > instance with all repairs stored in the database
     */
    public ArrayList<Repair> readAll() throws SQLException {

        ArrayList<Repair> repairs = new ArrayList<>();
        statement = connector.getConnection().prepareStatement("SELECT * FROM repairs");
        result = statement.executeQuery();

        while (result.next()){
            repairs.add(new Repair(result.getInt("repair_id"),
                                result.getString("problem"),
                                result.getInt("motorhome_id"),
                                result.getString("repair_status")));
        }
        statement = null;
        result = null;
        return repairs;
    }


    /**
     * @param repair repair object that should be added to the database
     */
    public void create(Repair repair) throws SQLException {

        System.out.println(repair);
        statement = connector.getConnection().prepareStatement("INSERT INTO repairs(problem, repair_status, motorhome_id) VALUES (?,?,?)");
        statement.setString(1, repair.getProblem());
        statement.setString(2, repair.getRepairStatus());
        statement.setInt(3, repair.getMotorhomeId());
        statement.execute();
        statement = null;

    }


    /**
     * @return Repair instance which is saved in the database at the given id, or null if no row was found
     */
    public Repair read(int repairId) throws SQLException {

        statement = connector.getConnection().prepareStatement("SELECT * FROM repairs WHERE repair_id=?");
        statement.setInt(1, repairId);
        result = statement.executeQuery();
        Repair repair = null;

        if (result.next()){
            repair = new Repair(result.getInt("repair_id"), result.getString("problem"), result.getInt("motorhome_id"), result.getString("repair_status"));
        }
        statement = null;
        result = null;
        return repair;
    }


    /**
     * Updates problem, repair_status, and motorhome_id in the database in the row with given ID
     * @param repair Repair object with new infomation to update
     */
    public void update(Repair repair) throws SQLException {

        statement = connector.getConnection().prepareStatement("UPDATE repairs SET problem=?, repair_status=?, motorhome_id=? WHERE repair_id=?");
        statement.setString(1, repair.getProblem());
        statement.setString(2, repair.getRepairStatus());
        statement.setInt(3, repair.getMotorhomeId());
        statement.setInt(4, repair.getRepairId());
        statement.execute();
        statement = null;
    }


    /**
     * Deletes row with given ID in the database
     * @param repairId ID of the repair that should be deleted
     */
    public void delete(int repairId) throws SQLException {

        statement = connector.getConnection().prepareStatement("DELETE FROM repairs WHERE repair_id=?");
        statement.setInt(1, repairId);
        statement.execute();
        statement = null;
    }

    /**
     * This method sets all references to the given motorhome ID to null
     * @param motorhomeId ID of motorhome that should be nullified
     */
    public void deleteForMotorhome(int motorhomeId) throws SQLException {
        statement = connector.getConnection().prepareStatement("DELETE FROM repairs where motorhome_id=?");
        statement.setInt(1, motorhomeId);
        statement.execute();
        statement = null;

    }
}
