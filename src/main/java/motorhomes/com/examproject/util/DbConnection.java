package motorhomes.com.examproject.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
        private final static String USERNAME = "motorhomes4";
        private final static String PASSWORD = "#project";
        private final static String CONNSTRING = "jdbc:mysql://den1.mysql4.gear.host/motorhomes4?useSSL=false";
        private static Connection connection;

        public static Connection getConnection() {

            try {
                if (connection == null){
                    connection = DriverManager.getConnection(CONNSTRING, USERNAME, PASSWORD);
                }
                return connection;

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }


}
