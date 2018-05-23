package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.Customer;
import motorhomes.com.examproject.util.DBConnector;
import motorhomes.com.examproject.util.DbConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @ Alicja Drankowska
 * todo comments
 */
@Repository
public class CustomersDbRepository{

    private PreparedStatement statement;
    private ResultSet result;
    private DBConnector connector;

    public CustomersDbRepository(){
    }

    @Autowired
    public void setConnector(DBConnector connector) {
        System.out.println("REPAIRS: OK");
        this.connector = connector;
    }

    public ArrayList<Customer> readAll() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();
        statement = connector.getConnection().prepareStatement("SELECT * FROM customers");
        result = statement.executeQuery();
        while (result.next()){
            customers.add(new Customer(result.getInt("customer_id"), result.getString("customers_name"), result.getString("driving_licence_nr")));
        }
        statement = null;
        result = null;
        return customers;
    }

    public void create(Customer customer) throws SQLException {
        System.out.println(customer);
        statement = connector.getConnection().prepareStatement("INSERT INTO customers(customers_name, driving_licence_nr) VALUES (?,?)");
        statement.setString(1, customer.getCustomerName());
        statement.setString(2, customer.getDrivingLicenceNr());
        statement.execute();
        statement = null;
    }

    public Customer read(String drivingLicenseNO) throws SQLException {
        statement = connector.getConnection().prepareStatement("SELECT * FROM customers WHERE driving_licence_nr = ?");
        statement.setString(1, drivingLicenseNO);
        result = statement.executeQuery();
        Customer customer = null;
        if (result.next()){
            customer = new Customer(result.getInt("customer_id"), result.getString("customers_name"), result.getString("driving_licence_nr"));
        }
        statement = null;
        result = null;
        return customer;
    }

    public Customer read(int customerId) throws SQLException {
        statement = connector.getConnection().prepareStatement("SELECT * FROM customers WHERE customer_id = ?");
        statement.setInt(1, customerId);
        result = statement.executeQuery();
        Customer customer = null;
        if (result.next()){
            customer = new Customer(result.getInt("customer_id"), result.getString("customers_name"), result.getString("driving_licence_nr"));
        }
        statement = null;
        result = null;
        return customer;
    }

    public void update(Customer customer) throws SQLException {
        statement = connector.getConnection().prepareStatement("UPDATE customers SET customers_name=?, driving_licence_nr=? WHERE customer_id=?");
        statement.setString(1, customer.getCustomerName());
        statement.setString(2, customer.getDrivingLicenceNr());
        statement.setInt(3, customer.getCustomerId());
        statement.execute();
        statement = null;
    }

    public void delete(int customerId) throws SQLException {
        statement = connector.getConnection().prepareStatement("DELETE FROM customers WHERE customer_id=?");
        statement.setInt(1, customerId);
        statement.execute();
        statement = null;
    }
}
