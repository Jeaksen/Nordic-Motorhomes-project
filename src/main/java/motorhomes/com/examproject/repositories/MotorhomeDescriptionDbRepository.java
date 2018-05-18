package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.MotorhomeDescription;
import motorhomes.com.examproject.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MotorhomeDescriptionDbRepository implements ICrudRepository<MotorhomeDescription> {

    private Connection connect;
    private PreparedStatement prepareSt;
    private ResultSet resultS;

    public MotorhomeDescriptionDbRepository() throws SQLException{
        this.connect = DbConnection.getConnection();
    }

    @Override
    public ArrayList<MotorhomeDescription> readAll() throws SQLException {
        ArrayList<MotorhomeDescription> motorhomeDescriptions = new ArrayList<>();

        prepareSt = connect.prepareStatement("SELECT * FROM descriptions");
        resultS = prepareSt.executeQuery();
        while (resultS.next()){
            motorhomeDescriptions.add(new MotorhomeDescription(resultS.getInt("description_id"), resultS.getString("brand"), resultS.getString("model"), resultS.getInt("base_price")));
        }

        prepareSt = null;
        resultS = null;
        return motorhomeDescriptions;
    }

    @Override
    public boolean create(MotorhomeDescription motorhomeDescription) throws SQLException {

        System.out.println(motorhomeDescription);
        prepareSt = connect.prepareStatement("INSERT INTO descriptions(brand, model, base_price) VALUES (?,?,?)");
        prepareSt.setString(1, motorhomeDescription.getMotorhomeBrand());
        prepareSt.setString(2, motorhomeDescription.getMotorhomeModel());
        prepareSt.setInt(3, motorhomeDescription.getMotorhomeBasePrice());
        boolean creationSuccessful = prepareSt.execute();
        prepareSt = null;
        return creationSuccessful;
    }

    @Override
    public MotorhomeDescription read(int motorhomeDescriptionId) throws SQLException {

        prepareSt = connect.prepareStatement("SELECT * FROM descriptions WHERE description_id = ?");
        prepareSt.setInt(1, motorhomeDescriptionId);
        resultS = prepareSt.executeQuery();
        MotorhomeDescription motorhomeDescription = null;

        if (resultS.next()){
            motorhomeDescription = new MotorhomeDescription(resultS.getInt("description_id"), resultS.getString("brand"), resultS.getString("model"), resultS.getInt("base_price"));
        }
        prepareSt = null;
        resultS = null;
        return motorhomeDescription;
    }

    @Override
    public void update(MotorhomeDescription motorhomeDescription) throws SQLException {

        prepareSt = connect.prepareStatement("UPDATE descriptions SET brand=?,model=?,base_price=? WHERE description_id = ?");
        prepareSt.setString(1, motorhomeDescription.getMotorhomeBrand());
        prepareSt.setString(2, motorhomeDescription.getMotorhomeModel());
        prepareSt.setInt(3, motorhomeDescription.getMotorhomeBasePrice());
        prepareSt.setInt(4, motorhomeDescription.getMotorhomeDescriptionId());
        prepareSt.execute();
        prepareSt = null;
    }

    @Override
    public void delete(int motorhomeDescriptionId) throws SQLException {

        prepareSt = connect.prepareStatement("DELETE FROM descriptions WHERE description_id=?");
        prepareSt.setInt(1, motorhomeDescriptionId);
        prepareSt.execute();
        prepareSt = null;
    }
}
