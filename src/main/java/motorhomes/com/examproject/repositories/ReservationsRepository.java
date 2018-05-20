package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.Reservation;
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


    public ReservationsRepository() throws SQLException {
        connection = DbConnection.getConnection();
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


    /**
     *
     * @param reservation
     * @return ID of created reservation or -1 if there was an error
     */
    public int create (Reservation reservation) throws SQLException {
        statement = connection.prepareStatement("INSERT INTO reservations (start_date, end_date, motorhome_id, customer_id) VALUES (?,?,?,?)");
        statement.setDate(1, Date.valueOf(reservation.getStartDate()));
        statement.setDate(2, Date.valueOf(reservation.getEndDate()));
        statement.setInt(3, reservation.getMotorhomeId());
        statement.setInt(4, reservation.getCustomersId());
        statement.execute();
        statement = connection.prepareStatement("SELECT reservation_id FROM reservations WHERE start_date = ? AND motorhome_id = ?");
        statement.setDate(1, Date.valueOf(reservation.getStartDate()));
        statement.setInt(2, reservation.getMotorhomeId());
        result = statement.executeQuery();
        if (result.next()) {
            return result.getInt("reservation_id");
        }
        return -1;
    }


    /**
     * This method updates: start_date, end_date, has_pickup, has_dropoff, has_accessories, reservation_status, and price
     */
    public void update (Reservation reservation) throws SQLException {
        statement = connection.prepareStatement("UPDATE reservations SET start_date=?, end_date=?, has_pickup=?, " +
                "has_dropoff=?, has_accessories=?, reservation_status=?, price=?;");
        statement.setDate(1, Date.valueOf(reservation.getStartDate()));
        statement.setDate(2, Date.valueOf(reservation.getEndDate()));
        statement.setBoolean(3, reservation.isHasPickUp());
        statement.setBoolean(4, reservation.isHasDropOff());
        statement.setBoolean(5, reservation.isHasAccessories());
        statement.setString(6, reservation.getReservationStatus());
        statement.setInt(7, reservation.getPrice());
        statement.execute();
    }

}
