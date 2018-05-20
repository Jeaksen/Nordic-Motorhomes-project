package motorhomes.com.examproject.controller;

import motorhomes.com.examproject.applicationLogic.ReservationsManager;
import motorhomes.com.examproject.model.Accessory;
import motorhomes.com.examproject.model.Customer;
import motorhomes.com.examproject.model.Motorhome;
import motorhomes.com.examproject.model.Reservation;
import motorhomes.com.examproject.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * @ Pawel Pohl
 */
@Controller
public class ReservationsController {

    private ReservationsManager reservationsManager;

    public ReservationsController() throws SQLException {
        this.reservationsManager = new ReservationsManager(new CustomersDbRepository(), new ReservationsRepository(),
                new MotorhomeDbRepository(new MotorhomeDescriptionDbRepository()), new PickupDbRepository(),
                new DropOffDbRepository(), new AccessoriesDbRepository(), new ReservationsAccessoriesRepository());
    }

    @GetMapping("/reservations")
    public String getReservations(Model model) {
        List<Reservation> reservations = reservationsManager.getReservations();
        model.addAttribute("reservations", reservations);
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
        motorhomes = reservationsManager.getAvailableMotorhomes(reservation);
        model.addAttribute("motorhomes", motorhomes);

        return "reservations/create/available_motorhomes";
    }

    @GetMapping("/save_motorhome")
    public String saveMotorhome(@RequestParam("motorhome_id") int motorhomesId, HttpServletRequest request) {
        Reservation reservation = (Reservation)request.getSession(true).getAttribute("reservation");
        reservationsManager.saveMotorhome(reservation, motorhomesId);
        return "reservations/create/customer_data";
    }

    @PostMapping("/save_customer_data")
    public String saveCustomer(@ModelAttribute Customer customer, HttpServletRequest request) {
        request.getSession().setAttribute("customer", customer);
        return "reservations/create/transport";
    }

    @PostMapping("/save_transport")
    public String saveTransport(Model model, HttpServletRequest request, @RequestParam(value="dropoff_distance", required=false) int dropDistance, @RequestParam(value="dropoff_location",
            required=false) String dropLocation, @RequestParam(value="pickup_distance", required=false) int pickDistance, @RequestParam(value="pickup_location", required=false) String pickLocation) {

        request.getSession().setAttribute("dropoff_distance",dropDistance);
        request.getSession().setAttribute("dropoff_location",dropLocation);
        request.getSession().setAttribute("pickup_distance",pickDistance);
        request.getSession().setAttribute("pickup_location",pickLocation);
        List<Accessory> accessories = reservationsManager.getAccessories();
        model.addAttribute("accessories", accessories);
        request.getSession().setAttribute("accessories", accessories);
        return "reservations/create/accessories";
    }

    @PostMapping("/save_accessories")
    public String saveAccessories(HttpServletRequest request, @RequestParam("quantities[]") int[] quantities) {
        request.getSession().setAttribute("quantities", quantities);
        return "reservations/create/summary";
    }

    @GetMapping("/confirm")
    public String confirmReservation(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Reservation reservation = (Reservation)session.getAttribute("reservation");
        Customer customer = (Customer)session.getAttribute("customer");
        int dropDistance = (int)session.getAttribute("dropoff_distance");
        String dropLocation = (String)session.getAttribute("dropoff_location");
        int pickDistance = (int)session.getAttribute("pickup_distance");
        String pickLocation = (String)session.getAttribute("pickup_location");
        List<Accessory> accessories = (List<Accessory>)request.getSession().getAttribute("accessories");
        int[] quantities = (int[])session.getAttribute("quantities");

        reservationsManager.saveCustomer(reservation, customer);
        reservationsManager.saveTransport(reservation, dropDistance, dropLocation, pickDistance, pickLocation);
        reservationsManager.saveAccessories(reservation, quantities, accessories);
        reservationsManager.updateReservation(reservation);
        System.out.println("Reservation: " + request.getSession(true).getAttribute("reservation") + "-----------------\n\n");

        session.removeAttribute("customer");
        session.removeAttribute("dropoff_distance");
        session.removeAttribute("dropoff_location");
        session.removeAttribute("pickup_distance");
        session.removeAttribute("pickup_location");
        session.removeAttribute("accessories");
        session.removeAttribute("quantities");
        session.removeAttribute("reservation");
        return "redirect:/reservations";
    }


}