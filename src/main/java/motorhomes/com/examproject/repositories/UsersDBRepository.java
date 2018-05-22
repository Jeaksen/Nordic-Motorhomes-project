package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.User;
import motorhomes.com.examproject.util.DBConnector;
import motorhomes.com.examproject.util.DbConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ Pawel Pohl
 * This class is used to manipulate data in the database storing users
 */
@Repository
public class UsersDBRepository {


    private PreparedStatement statement;
    private ResultSet result;
    private DBConnector connector;


    public UsersDBRepository(){
    }

    @Autowired
    public void setConnector(DBConnector connector) {
        System.out.println("USERS: OK");
        this.connector = connector;
    }

    /**
     * @param username username of User who is searched for
     * @return User instance with username and password or null if no user was found
     */
    public User read(String username) throws SQLException {
        statement = connector.getConnection().prepareStatement("SELECT user_id, password FROM users WHERE username=?;");
        statement.setString(1, username);
        result = statement.executeQuery();

        User savedUser = null;
        if (result.next()) {
            savedUser = new User(result.getInt("user_id"), username, result.getString("password"));
        }
        statement = null;
        result = null;
        return savedUser;
    }

    /**
     * @param user User object with username and password which should be saved in the database
     */
    public void create(User user) throws SQLException{

        statement = connector.getConnection().prepareStatement("INSERT INTO users(username, password) VALUE (?,?);");
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.execute();

        statement = null;
        result = null;
    }
}
