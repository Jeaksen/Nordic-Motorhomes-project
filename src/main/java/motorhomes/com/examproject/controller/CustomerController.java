package motorhomes.com.examproject.controller;
import motorhomes.com.examproject.applicationLogic.CustomerManager;
import motorhomes.com.examproject.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ AlexandraCaragata
 */
@Controller
public class CustomerController {
    private CustomerManager customerManager;

    @Autowired
    public CustomerController (CustomerManager customerManager){
        this.customerManager= customerManager;
    }

    @GetMapping("/customers")
    public String getCustomers(Model model){
        List<Customer> customers= customerManager.getCustomers();
        model.addAttribute("customers", customers);
        return"customer/customers";
    }

    @GetMapping ("/new_customer")
    public String newCustomer(){
        return "customer/newcustomer";
    }

    @PostMapping("/new_customer")
    public String newCustomer(@ModelAttribute Customer customer){
        customerManager.createCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/update_customer")
    public String updateCustomer(@RequestParam("customerId") int customerId, Model model){
        Customer customer=customerManager.getCustomer(customerId);
        model.addAttribute("customer", customer);
        return"customer/updatecustomer";
    }

    @PostMapping("update_customer")
    public String updateCustomer(@ModelAttribute Customer customer){
        customerManager.updateCustomer(customer);
        return "redirect:/customers";

    }
    @GetMapping("/delete_customer")
    public String deleteCustomer(@RequestParam("customerId") int customerId, Model model){
        Customer customer=customerManager.getCustomer(customerId);
        model.addAttribute("customer", customer);
        return"customer/deletecustomer";
    }


    @PostMapping("/delete_customer")
    public String deleteCustomer(@ModelAttribute Customer customer){
        customerManager.deleteCustomer(customer.getCustomerId());
        return"redirect:/customers";
    }


}
