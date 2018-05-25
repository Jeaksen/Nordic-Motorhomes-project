package motorhomes.com.examproject.applicationLogic;

import motorhomes.com.examproject.model.Customer;
import motorhomes.com.examproject.repositories.CustomersDbRepository;
import motorhomes.com.examproject.repositories.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * @ Alexandra Caragata
 */
@Component
public class CustomerManager {

    private CustomersDbRepository customersDbRepository;
    private  ReservationsRepository reservationsRepository;

        @Autowired
        public CustomerManager (CustomersDbRepository customersDbRepository, ReservationsRepository reservationsRepository) {
            this.customersDbRepository=customersDbRepository;
            this.reservationsRepository = reservationsRepository;
        }

        public List<Customer> getCustomers(){
            try {
                List<Customer> customers=customersDbRepository.readAll();
                return customers;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void createCustomer( Customer customer){
            try {
                customersDbRepository.create(customer);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public Customer getCustomer(int customerId) {
            try {
                Customer customer= customersDbRepository.read(customerId);
                return customer;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void updateCustomer(Customer customer){
            try {
                customersDbRepository.update(customer);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void deleteCustomer(int customerId){
            try {
                reservationsRepository.nullifyCustomer(customerId);
                customersDbRepository.delete(customerId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


