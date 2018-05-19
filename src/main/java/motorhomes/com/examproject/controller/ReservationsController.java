package motorhomes.com.examproject.controller;

import motorhomes.com.examproject.applicationLogic.ReservationsManager;
import motorhomes.com.examproject.model.Accessory;
import motorhomes.com.examproject.model.Customer;
import motorhomes.com.examproject.model.Motorhome;
import motorhomes.com.examproject.model.Reservation;
import motorhomes.com.examproject.repositories.CustomersDbRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "reservations/add_reservation";
    }

    @PostMapping("/add_reservation")
    public String startReservation(@RequestParam("start_date") String startDate,
                                   @RequestParam("end_date") String endDate, HttpServletRequest request, Model model) {
        Reservation reservation = reservationsManager.startReservation(LocalDate.parse(startDate), LocalDate.parse(endDate));
        //todo delete when reservation if finished
        request.getSession().setAttribute("reservation", reservation);
        List<Motorhome> motorhomes;
        motorhomes = reservationsManager.getAvailableMotorhomes();
        model.addAttribute("motorhomes", motorhomes);
        return "reservations/available_motorhomes";
    }

    @PostMapping("/add_motorhome")
    public String saveMotorhome(@RequestParam("motorhome_id") int motorhomesId, HttpServletRequest request) {
        Reservation reservation = (Reservation)request.getSession().getAttribute("reservation");
        //todo does it change the object in session?
        reservationsManager.saveMotorhome(reservation, motorhomesId);
        return "reservations/customer_data";
    }

    @PostMapping("/save_customer_data")
    public String saveCustomer(@ModelAttribute Customer customer) {

        return "reservations/transport";
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
        return "reservations/accessories";
    }

    @PostMapping("/save_accessories")
    public String saveAccessories(HttpServletRequest request, Model model, @RequestParam("quantities[]") int[] quantities) {
        return "reservations/summary";
    }

    @GetMapping("/confirm")
    public String confirmReservation(HttpServletRequest request) {
        request.getSession().removeAttribute("reservation");
        return "redirect:/reservations";
    }


}
