package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.User;
import motorhomes.com.examproject.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ Pawel Pohl
 * todo comments
 */
public class UsersDBRepository {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;


    public UsersDBRepository() throws SQLException {
        connection = DbConnection.getConnection();
    }

    public User read(String username) throws SQLException {
        statement = connection.prepareStatement("SELECT user_id, password FROM users WHERE username=?;");
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

    public void create(User user) throws SQLException{

        statement = connection.prepareStatement("INSERT INTO users(username, password) VALUE (?,?);");
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.execute();

        statement = null;
        result = null;
    }
}
