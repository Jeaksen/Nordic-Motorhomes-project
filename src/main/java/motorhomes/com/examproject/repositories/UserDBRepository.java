package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.User;

import java.sql.SQLException;

public class UserDBRepository {

    public User read(String username) throws SQLException {
        return new User(1, "Popo", "*");
    }

    public void create(User user) throws SQLException{

    }


}
