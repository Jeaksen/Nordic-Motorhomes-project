package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.Customer;
import motorhomes.com.examproject.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @ Alicja Drankowska
 * todo comments
 */
public class CustomersDbRepository implements ICrudRepository<Customer> {

    private Connection conn;
    private PreparedStatement preSt;
    private ResultSet res;

    public CustomersDbRepository() throws SQLException{
        this.conn = DbConnection.getConnection();
    }

    @Override
    public ArrayList<Customer> readAll() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();
        preSt = conn.prepareStatement("SELECT * FROM customer");
        res = preSt.executeQuery();

        while (res.next()){
            customers.add(new Customer(res.getInt("customer_id"), res.getString("customers_name"), res.getString("driving_licence_nr")));
        }
        preSt = null;
        res = null;
        return customers;
    }

    @Override
    public boolean create(Customer customer) throws SQLException {
        System.out.println(customer);
        preSt = conn.prepareStatement("INSERT INTO customers(customers_name, driving_licence_nr) VALUES (?,?)");
        preSt.setString(1, customer.getCustomerName());
        preSt.setString(2, customer.getDrivingLicenseNr());
        boolean creationSuccessful = preSt.execute();
        preSt = null;
        return creationSuccessful;
    }

    @Override
    public Customer read(int customerId) throws SQLException {
        preSt = conn.prepareStatement("SELECT * FROM customers WHERE customer_id = ?");
        preSt.setInt(1, customerId);
        res = preSt.executeQuery();
        Customer customer = null;

        if (res.next()){
            customer = new Customer(res.getInt("customer_id"), res.getString("customers_name"), res.getString("driving_licence_nr"));
        }
        preSt = null;
        res = null;
        return customer;
    }

    @Override
    public void update(Customer customer) throws SQLException {

        preSt = conn.prepareStatement("UPDATE customers SET customers_name=?, driving_licence_nr=? WHERE customer_id=?");
        preSt.setString(1, customer.getCustomerName());
        preSt.setString(2, customer.getDrivingLicenseNr());
        preSt.setInt(3, customer.getCustomerId());
        preSt.execute();
        preSt = null;
    }

    @Override
    public void delete(int customerId) throws SQLException {
        preSt = conn.prepareStatement("DELETE FROM customers WHERE customer_id=?");
        preSt.setInt(1, customerId);
        preSt.execute();
        preSt = null;
    }
}
