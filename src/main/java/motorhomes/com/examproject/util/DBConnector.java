package motorhomes.com.examproject.util;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @ Pawel Pohl
 * This class is used to connect to the database
 * It main purpose is to prevent database disconnection
 * It does it through checking if a certain amount of time has passes since last connection (10min)
 */
@Component
public class DBConnector {
    private final String USERNAME = "motorhomes4";
    private final String PASSWORD = "#project";
    private final String CONNSTRING = "jdbc:mysql://den1.mysql4.gear.host/motorhomes4?useSSL=false";
    private Connection connection;
    private long connectionTime;
    private final int disconnectionTime = 600000;

    public DBConnector() throws SQLException {
        this.establishNewConnection();
    }

    public Connection getConnection() throws SQLException {
        this.refreshConnection();
        return this.connection;
    }

    private void refreshConnection() throws SQLException {
        if (System.currentTimeMillis() - this.connectionTime > disconnectionTime) {
            this.establishNewConnection();
        }
    }

    private void establishNewConnection() throws SQLException {
        System.out.println("Connecting to the database...");
        this.connectionTime = System.currentTimeMillis();
        this.connection = DriverManager.getConnection(CONNSTRING, USERNAME, PASSWORD);
    }


}
