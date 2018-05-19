package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.Customer;
import motorhomes.com.examproject.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * todo comments
 */
public class CustomersDbRepository implements ICrudRepository<Customer> {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public CustomersDbRepository() throws SQLException{
        this.connection = DbConnection.getConnection();
    }

    @Override
    public ArrayList<Customer> readAll() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();
        statement = connection.prepareStatement("SELECT * FROM customers");
        resultSet = statement.executeQuery();

        while (resultSet.next()){
            customers.add(new Customer(resultSet.getInt("customer_id"), resultSet.getString("customers_name"), resultSet.getString("driving_licence_nr")));
        }
        statement = null;
        resultSet = null;
        return customers;
    }

    @Override
    public boolean create(Customer customer) throws SQLException {
        System.out.println(customer);
        statement = connection.prepareStatement("INSERT INTO customers(customers_name, driving_licence_nr) VALUES (?,?)");
        statement.setString(1, customer.getCustomerName());
        statement.setString(2, customer.getDrivingLicenseNr());
        boolean creationSuccessful = statement.execute();
        statement = null;
        return creationSuccessful;
    }

    @Override
    public Customer read(int customerId) throws SQLException {
        statement = connection.prepareStatement("SELECT * FROM customers WHERE customer_id = ?");
        statement.setInt(1, customerId);
        resultSet = statement.executeQuery();
        Customer customer = null;

        if (resultSet.next()){
            customer = new Customer(resultSet.getInt("customer_id"), resultSet.getString("customers_name"), resultSet.getString("driving_licence_nr"));
        }
        statement = null;
        resultSet = null;
        return customer;
    }

    @Override
    public void update(Customer customer) throws SQLException {

        statement = connection.prepareStatement("UPDATE customers SET customers_name=?, driving_licence_nr=? WHERE customer_id=?");
        statement.setString(1, customer.getCustomerName());
        statement.setString(2, customer.getDrivingLicenseNr());
        statement.setInt(3, customer.getCustomerId());
        statement.execute();
        statement = null;
    }

    @Override
    public void delete(int customerId) throws SQLException {
        statement = connection.prepareStatement("DELETE FROM customers WHERE customer_id=?");
        statement.setInt(1, customerId);
        statement.execute();
        statement = null;
    }
}
