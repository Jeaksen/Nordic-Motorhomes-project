package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.util.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * @ Pawel Pohl
 */
public class ReservationsRepository {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;
    private long connectionTime;

    public ReservationsRepository() throws SQLException {
        connection = DbConnection.getConnection();
        connectionTime = System.currentTimeMillis();
    }


    /**
     * This method goes through all the reservations and deletes IDs of motorhomes that are not free in entered period of time
     * @param startDate start date of a reservation
     * @param endDate end date of a reservation
     * @param allMotorhomeIds List object with IDs of all motorhomes saved in the database
     * @throws SQLException When there is an error while querying the data
     */
    public void getAvailableMotorhomesIds(LocalDate startDate, LocalDate endDate, List<Integer> allMotorhomeIds) throws SQLException {

        statement = connection.prepareStatement("SELECT motorhome_id from reservations " +
                "where end_date >= ? AND start_date <= ? " +
                "AND (start_date = ? " +
                "OR start_date=? " +
                "OR end_date = ? " +
                "OR end_date = ? " +
                "OR (start_date > ? AND start_date < ?) " +
                "OR (start_date > ? AND end_date < ?) " +
                "OR (end_date > ? AND end_date < ?))");
        statement.setDate(1, Date.valueOf(startDate));
        statement.setDate(4, Date.valueOf(startDate));
        statement.setDate(5, Date.valueOf(startDate));
        statement.setDate(7, Date.valueOf(startDate));
        statement.setDate(9, Date.valueOf(startDate));
        statement.setDate(11, Date.valueOf(startDate));
        statement.setDate(2, Date.valueOf(endDate));
        statement.setDate(3, Date.valueOf(endDate));
        statement.setDate(6, Date.valueOf(endDate));
        statement.setDate(8, Date.valueOf(endDate));
        statement.setDate(10, Date.valueOf(endDate));
        statement.setDate(12, Date.valueOf(endDate));
        result = statement.executeQuery();

        while (result.next()){
            allMotorhomeIds.remove((Integer)result.getInt("motorhome_id"));
        }
    }

    private void tryReconnect() throws SQLException {
        if (System.currentTimeMillis() - this.connectionTime > 600000){
            this.connection = DbConnection.getConnection();
        }
    }

}
