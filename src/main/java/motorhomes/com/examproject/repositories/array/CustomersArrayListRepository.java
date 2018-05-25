package motorhomes.com.examproject.repositories.array;

import motorhomes.com.examproject.model.Customer;

import java.util.ArrayList;

/**
 * @ Alicja Drankowska
 */
public class CustomersArrayListRepository implements ICrudRepository<Customer> {

    private ArrayList<Customer> customers = new ArrayList<>();

    public CustomersArrayListRepository(){
        //add customers here
    }

    @Override
    public ArrayList<Customer> readAll() throws Exception {
        //code reading from an ArrayList
        return customers;
    }

    @Override
    public boolean create(Customer customer) throws Exception {
        //code adding from an ArrayList
        customers.add(customer);
        customer.setCustomerId(customers.size());

        return true;
    }

    @Override
    public Customer read(int id) throws Exception {
        return customers.get(id - 1);
    }

    @Override
    public void update(Customer customer) throws Exception {
        for (Customer cu : customers) {

            if (cu.getCustomerId() == customer.getCustomerId()){
                customers.remove(cu);
                customers.add(customer);
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        for (Customer c : customers) {
            if (c.getCustomerId() == id){
                customers.remove(id-1);
            }
        }
    }

}
