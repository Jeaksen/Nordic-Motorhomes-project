package motorhomes.com.examproject.aplicationLogic;

import motorhomes.com.examproject.model.User;
import motorhomes.com.examproject.repositories.UsersDBRepository;

import java.sql.SQLException;


/**
 * This class provides functionality for managing the homepage:
 * - login
 * - register
 */
public class HomepageManager {
    private UsersDBRepository usersRepository;

    public HomepageManager(UsersDBRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * This method checks if entered userdata matches data saved in a database
     * @param checkedUser User object which save username and password entered by a user
     * @return
     * 1 if checked user's data match saved user data
     * 0 if an exception was thrown
     * -1 if password doesn't match, or entered password is empty
     * -2 if user with such username wasn't found or username is empty
     */
    public byte login(User checkedUser){
        if (checkedUser.getPassword().isEmpty()) return -1;
        if (checkedUser.getUsername().isEmpty()) return -2;

        User savedUser;
        try {
            savedUser = usersRepository.read(checkedUser.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        if (savedUser == null){
            return -2;
        }
        if(checkedUser.getPassword().equals(savedUser.getPassword())){
            return 1;
        }
        return -1;
    }


    /**
     * This method is used to save a new user in a repository
     * @param user User object that should be saved
     * @return
     * 1 if saving was successful,
     * 0 if an exception was thrown,
     * -1 if entered password is empty,
     * -2 if username is empty or already taken
     *
     */
    public byte register(User user){
        if (user.getPassword().isEmpty()) return -1;
        if (user.getUsername().isEmpty()) return -2;

        try {
            if (usersRepository.read(user.getUsername()) != null){
                return -2;
            }
            usersRepository.create(user);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
