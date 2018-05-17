package motorhomes.com.examproject.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
        private final static String USERNAME = "motorhomes4";
        private final static String PASSWORD = "#project";
        private final static String CONNSTRING = "jdbc:mysql://den1.mysql4.gear.host/motorhomes4?useSSL=false";
        private static Connection connection;

        public static Connection getConnection() throws SQLException {
            System.out.println("I'm here");
            if (connection == null || !connection.isValid(1)){
                connection = DriverManager.getConnection(CONNSTRING, USERNAME, PASSWORD);
            }
            return connection;
        }
}
