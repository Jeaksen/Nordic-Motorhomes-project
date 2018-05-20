package motorhomes.com.examproject.controller;

import motorhomes.com.examproject.applicationLogic.ReservationsManager;
import motorhomes.com.examproject.model.Accessory;
import motorhomes.com.examproject.model.Customer;
import motorhomes.com.examproject.model.Motorhome;
import motorhomes.com.examproject.model.Reservation;
import motorhomes.com.examproject.repositories.CustomersDbRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationsController {

    private ReservationsManager reservationsManager;

    public ReservationsController() throws SQLException {
        this.reservationsManager = new ReservationsManager(new CustomersDbRepository());
    }

    @GetMapping("/reservations")
    public String getReservations(Model model) {
        List<Motorhome> motorhomes = reservationsManager.getReservations();
        model.addAttribute("motorhomes", motorhomes);
        return "reservations/reservations";
    }

    @GetMapping("/add_reservation")
    public String startReservation() {
        return "reservations/create/add_reservation";
    }

    @PostMapping("/add_reservation")
    public String startReservation(@RequestParam("start_date") String startDate,
                                   @RequestParam("end_date") String endDate, HttpServletRequest request, Model model) {

        Reservation reservation = reservationsManager.startReservation(LocalDate.parse(startDate), LocalDate.parse(endDate));
        request.getSession(true).setAttribute("reservation", reservation);
        List<Motorhome> motorhomes;
        motorhomes = reservationsManager.getAvailableMotorhomes();
        model.addAttribute("motorhomes", motorhomes);
        return "reservations/create/available_motorhomes";
    }

    @PostMapping("/add_motorhome")
    public String saveMotorhome(@RequestParam("motorhome_id") int motorhomesId, HttpServletRequest request) {
        Reservation reservation = (Reservation)request.getSession(true).getAttribute("reservation");
        //todo does it change the object in session?
        reservationsManager.saveMotorhome(reservation, motorhomesId);
        return "reservations/create/customer_data";
    }

    @PostMapping("/save_customer_data")
    public String saveCustomer(@ModelAttribute Customer customer, HttpServletRequest request) {
        reservationsManager.saveCustomer((Reservation)request.getSession().getAttribute("reservation"), customer);
        return "reservations/create/transport";
    }

    @PostMapping("/save_transport")
    public String saveTransport(Model model, HttpServletRequest request,
                                @RequestParam(value="dropoff_distance", required=false) int dropDistance,
                                @RequestParam(value="dropoff_location", required=false) String dropLocation,
                                @RequestParam(value="pickup_distance", required=false) int pickDistance,
                                @RequestParam(value="pickup_location", required=false) String pickLocation) {

        List<Accessory> accessories = new ArrayList<>();
        accessories.add(new Accessory(1,"cos", 100));
        //accessories.add(new Accessory(2, "cos2", 50));
        model.addAttribute("accessories", accessories);
        reservationsManager.saveTransport((Reservation)request.getSession().getAttribute("reservation"), dropDistance, dropLocation, pickDistance, pickLocation);
        return "reservations/create/accessories";
    }

    @PostMapping("/save_accessories")
    public String saveAccessories(HttpServletRequest request, Model model, @RequestParam("quantities[]") int[] quantities) {
        reservationsManager.saveAccessories((Reservation)request.getSession().getAttribute("reservation"), quantities);
        return "reservations/create/summary";
    }

    @GetMapping("/confirm")
    public String confirmReservation(HttpServletRequest request) {
        System.out.println("Reservation: " + request.getSession(true).getAttribute("reservation") + "-----------------\n\n");
        request.getSession(true).removeAttribute("reservation");
        return "redirect:/reservations";
    }


}
