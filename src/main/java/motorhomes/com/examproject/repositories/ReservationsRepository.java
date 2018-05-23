package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.Reservation;
import motorhomes.com.examproject.util.DBConnector;
import motorhomes.com.examproject.util.DbConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * @ Pawel Pohl
 * This class is used to manipulate data in the database storing reservations
 */
@Repository
public class ReservationsRepository {

    private PreparedStatement statement;
    private ResultSet resultSet;
    private DBConnector connector;


    public ReservationsRepository() throws SQLException {
    }

    @Autowired
    public void setConnector(DBConnector connector) {
        System.out.println("RESERVATIONS: OK");
        this.connector = connector;
    }


    /**
     * This method goes through all the reservations and deletes IDs of motorhomes that are not free in entered period of time
     * @param startDate start date of a reservation
     * @param endDate end date of a reservation
     * @param allMotorhomeIds List object with IDs of all motorhomes saved in the database
     * @throws SQLException When there is an error while querying the data
     */
    public void getAvailableMotorhomesIds(LocalDate startDate, LocalDate endDate, List<Integer> allMotorhomeIds) throws SQLException {

        statement = connector.getConnection().prepareStatement("SELECT motorhome_id from reservations " +
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
        resultSet = statement.executeQuery();

        while (resultSet.next()){
            allMotorhomeIds.remove((Integer) resultSet.getInt("motorhome_id"));
        }
        statement = null;
        resultSet = null;
        
    }


    /**
     * This method creates new row in the database with: start_date, end_date, motorhome_id and customer_id
     * @return ID of created reservation or -1 if there was an error
     */
    public int create (Reservation reservation) throws SQLException {
        statement = connector.getConnection().prepareStatement("INSERT INTO reservations (start_date, end_date, motorhome_id, customer_id) VALUES (?,?,?,?)");
        statement.setDate(1, Date.valueOf(reservation.getStartDate()));
        statement.setDate(2, Date.valueOf(reservation.getEndDate()));
        statement.setInt(3, reservation.getMotorhomeId());
        statement.setInt(4, reservation.getCustomerId());
        statement.execute();
        statement = connector.getConnection().prepareStatement("SELECT reservation_id FROM reservations WHERE start_date = ? AND motorhome_id = ?");
        statement.setDate(1, Date.valueOf(reservation.getStartDate()));
        statement.setInt(2, reservation.getMotorhomeId());
        resultSet = statement.executeQuery();
        int reservationId = -1;
        if (resultSet.next()) {
            reservationId = resultSet.getInt("reservation_id");
        }
        statement = null;
        resultSet = null;
        return reservationId;
    }


    /**
     * This method updates: start_date, end_date, has_pickup, has_dropoff, has_accessories, reservation_status, and price of reservation
     */
    public void update (Reservation reservation) throws SQLException {
        statement = connector.getConnection().prepareStatement("UPDATE reservations SET start_date=?, end_date=?, has_pickup=?, " +
                "has_dropoff=?, has_accessories=?, reservation_status=?, price=? WHERE reservation_id=?;");
        statement.setDate(1, Date.valueOf(reservation.getStartDate()));
        statement.setDate(2, Date.valueOf(reservation.getEndDate()));
        statement.setBoolean(3, reservation.isHasPickUp());
        statement.setBoolean(4, reservation.isHasDropOff());
        statement.setBoolean(5, reservation.isHasAccessories());
        statement.setString(6, reservation.getStatus());
        statement.setInt(7, reservation.getPrice());
        statement.setInt(8, reservation.getReservationId());
        statement.execute();
        statement = null;
    }

    /**
     * @return List object with all reservations stored in the database
     */
    public List<Reservation> readAll() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation;
        statement = connector.getConnection().prepareStatement("SELECT * FROM reservations");
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            reservation = new Reservation();
            reservation.setReservationId(resultSet.getInt("reservation_id"));
            reservation.setStartDate(resultSet.getDate("start_date").toLocalDate());
            reservation.setEndDate(resultSet.getDate("end_date").toLocalDate());
            reservation.setHasPickUp(resultSet.getBoolean("has_pickup"));
            reservation.setHasDropOff(resultSet.getBoolean("has_dropoff"));
            reservation.setHasAccessories(resultSet.getBoolean("has_accessories"));
            reservation.setPrice(resultSet.getInt("price"));
            reservation.setStatus(resultSet.getString("reservation_status"));
            reservation.setMotorhomeId(resultSet.getInt("motorhome_id"));
            reservation.setCustomerId(resultSet.getInt("customer_id"));
            reservations.add(reservation);
        }
        return reservations;
    }

    /**
     * @param reservationId ID of reservation in database
     * @return Reservation object or null if no reservation was found
     */
    public Reservation read(int reservationId) throws SQLException {
        statement = connector.getConnection().prepareStatement("SELECT * FROM reservations WHERE reservation_id=?");
        statement.setInt(1, reservationId);
        resultSet = statement.executeQuery();
        Reservation reservation = null;
        if (resultSet.next()) {
            reservation = new Reservation();
            reservation.setReservationId(resultSet.getInt("reservation_id"));
            reservation.setStartDate(resultSet.getDate("start_date").toLocalDate());
            reservation.setEndDate(resultSet.getDate("end_date").toLocalDate());
            reservation.setHasPickUp(resultSet.getBoolean("has_pickup"));
            reservation.setHasDropOff(resultSet.getBoolean("has_dropoff"));
            reservation.setHasAccessories(resultSet.getBoolean("has_accessories"));
            reservation.setPrice(resultSet.getInt("price"));
            reservation.setStatus(resultSet.getString("reservation_status"));
            reservation.setMotorhomeId(resultSet.getInt("motorhome_id"));
            reservation.setCustomerId(resultSet.getInt("customer_id"));
        }
        return reservation;
    }

    /**
     * This method deletes a reservation with given ID
     */
    public void delete (int reservationId) throws SQLException {
        statement = connector.getConnection().prepareStatement("DELETE FROM reservations WHERE reservation_id=?");
        statement.setInt(1, reservationId);
        statement.execute();
        statement = null;
    }

    /**
     * This method sets all references to the given motorhome ID to null
     * @param motorhomeId ID of motorhome that should be nullified
     */
    public void nullifyMotorhome(int motorhomeId) throws SQLException {
        statement = connector.getConnection().prepareStatement("UPDATE reservations SET motorhome_id=null where motorhome_id=?");
        statement.setInt(1, motorhomeId);
        statement.execute();
        statement = null;

    }

    /**
     * This method sets all references to the given customer ID to null
     * @param customerId ID of customer that should be nullified
     */
    public void nullifyCustomer(int customerId) throws SQLException {
        statement = connector.getConnection().prepareStatement("UPDATE reservations SET customer_id=null where customer_id=?");
        statement.setInt(1, customerId);
        statement.execute();
        statement = null;

    }

}
