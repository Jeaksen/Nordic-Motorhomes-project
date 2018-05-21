package motorhomes.com.examproject.controller;

import motorhomes.com.examproject.applicationLogic.ReservationsManager;
import motorhomes.com.examproject.model.*;
import motorhomes.com.examproject.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.Inet4Address;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String saveTransport(Model model, HttpServletRequest request,
                                @RequestParam(value="dropoff_distance", required=false) int dropDistance, @RequestParam(value="dropoff_location", required=false) String dropLocation,
                                @RequestParam(value="pickup_distance", required=false) int pickDistance, @RequestParam(value="pickup_location", required=false) String pickLocation) {

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
        return "redirect:/confirm";
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
        //todo calculate price
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

    @GetMapping("/details_reservation")
    public String getDetails(Model model, @RequestParam("reservation_id") int reservationId) {
        Reservation reservation = reservationsManager.getReservation(reservationId);
        model.addAttribute("reservation", reservation);
        model.addAttribute("customer", reservationsManager.getCustomer(reservation.getCustomerId()));
        model.addAttribute("motorhome", reservationsManager.getMotorhome(reservation.getMotorhomeId()));
        Map<Accessory, Integer> accessories = new HashMap<>();
        reservation.getAccessories().forEach((k,v) -> accessories.put(reservationsManager.getAccessory(k),v));
        model.addAttribute("accessories", accessories);
        if (reservation.isHasDropOff()) {
            DropOff dropOff = reservationsManager.getDropOff(reservationId);
            model.addAttribute("dropoff", dropOff);
        }
        if (reservation.isHasPickUp()) {
            PickUp pickUp = reservationsManager.getPickUp(reservationId);
            model.addAttribute("pickup", pickUp);
        }
        return "reservations/details";
    }


    @GetMapping("/update_reservation")
    public String updateReservation(HttpSession session, @RequestParam("reservation_id") int reservationId) {
        Reservation reservation = reservationsManager.getReservation(reservationId);
        DropOff dropOff = reservationsManager.getDropOff(reservationId)!=null?reservationsManager.getDropOff(reservationId):new DropOff(0,"",0);
        PickUp pickUp = reservationsManager.getPickUp(reservationId)!=null?reservationsManager.getPickUp(reservationId):new PickUp(0,"", 0);
        List<Accessory> accessories = reservationsManager.getAccessories();
        Map<Integer, Integer> accessoryIdQuantityMap = reservation.getAccessories();
        Map<Accessory, Integer> accessoryQuantityMap = new HashMap<>();
        accessories.forEach((accessory) -> accessoryQuantityMap.put(accessory, accessoryIdQuantityMap.getOrDefault(accessory.getId(), 0)) );
        session.setAttribute("dropoff", dropOff);
        session.setAttribute("pickup", pickUp);
        session.setAttribute("reservation", reservation);
        session.setAttribute("accessories", accessoryQuantityMap);
        session.setAttribute("reservation", reservation);

        return "reservations/update";
    }

    @PostMapping("/update_reservation")
    public String saveUpdate(HttpSession session, @RequestParam(value="dropOffDistance", required=false) int dropDistance, @RequestParam(value="dropOffLocation", required=false) String dropLocation,
                             @RequestParam(value="pickUpDistance", required=false) int pickDistance, @RequestParam(value="pickUpLocation", required=false) String pickLocation,
                             @RequestParam("quantities[]") int[] quantities, @RequestParam("status") String reservationStatus, @RequestParam("price") int price) {
        PickUp pickUp = new PickUp(0, pickLocation.trim(), pickDistance);
        DropOff dropOff = new DropOff(0, dropLocation.trim(), dropDistance);
        reservationsManager.updateReservation((Reservation)session.getAttribute("reservation"), reservationStatus, price,
                (PickUp)session.getAttribute("pickup"), pickUp, (DropOff)session.getAttribute("dropoff"), dropOff, quantities,(Map<Accessory, Integer>)session.getAttribute("accessories"));
        System.out.println(session.getAttribute("reservation"));
        return "redirect:/reservations";
    }


}