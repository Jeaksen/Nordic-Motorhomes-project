package motorhomes.com.examproject.controller;

import motorhomes.com.examproject.applicationLogic.ReservationsManager;
import motorhomes.com.examproject.model.*;
import motorhomes.com.examproject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Pawel Pohl
 */
@Controller
public class ReservationsController {

    private ReservationsManager reservationsManager;

    @Autowired
    public ReservationsController(ReservationsManager reservationsManager){
        this.reservationsManager = reservationsManager;
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
                                   @RequestParam("end_date") String endDate, HttpSession session, Model model) {

        Reservation reservation = reservationsManager.startReservation(LocalDate.parse(startDate), LocalDate.parse(endDate));
        if (reservation == null) {
            return "redirect:/add_reservation";
        }
        session.setAttribute("reservation", reservation);
        List<Motorhome> motorhomes;
        motorhomes = reservationsManager.getAvailableMotorhomes(reservation);
        if (motorhomes == null || motorhomes.size() == 0) {
            return "redirect:/add_reservation";
        }
        model.addAttribute("motorhomes", motorhomes);

        return "reservations/create/available_motorhomes";
    }

    @GetMapping("/save_motorhome")
    public String saveMotorhome(@RequestParam("motorhome_id") int motorhomesId, HttpSession session) {
        Reservation reservation = (Reservation)session.getAttribute("reservation");
        reservationsManager.saveMotorhome(reservation, motorhomesId);
        return "reservations/create/customer_data";
    }

    @PostMapping("/save_customer_data")
    public String saveCustomer(@ModelAttribute Customer customer, HttpSession session) {
        session.setAttribute("customer", customer);
        return "reservations/create/transport";
    }

    @PostMapping("/save_transport")
    public String saveTransport(Model model, HttpSession session,
                                @RequestParam(value="dropoff_distance", required=false) int dropDistance, @RequestParam(value="dropoff_location", required=false) String dropLocation,
                                @RequestParam(value="pickup_distance", required=false) int pickDistance, @RequestParam(value="pickup_location", required=false) String pickLocation) {

        session.setAttribute("dropoff_distance",dropDistance);
        session.setAttribute("dropoff_location",dropLocation);
        session.setAttribute("pickup_distance",pickDistance);
        session.setAttribute("pickup_location",pickLocation);
        List<Accessory> accessories = reservationsManager.getAllAccessories();
        model.addAttribute("accessories", accessories);
        session.setAttribute("accessories", accessories);
        return "reservations/create/accessories";
    }

    @PostMapping("/save_accessories")
    public String saveAccessories(HttpSession session, @RequestParam("quantities[]") int[] quantities) {
        session.setAttribute("quantities", quantities);
        return "redirect:/confirm";
    }

    @GetMapping("/confirm")
    public String confirmReservation(HttpSession session) {
//        Reservation reservation = (Reservation)session.getAttribute("reservation");
//        Customer customer = (Customer)session.getAttribute("customer");
//        int dropDistance = (int)session.getAttribute("dropoff_distance");
//        String dropLocation = (String)session.getAttribute("dropoff_location");
//        int pickDistance = (int)session.getAttribute("pickup_distance");
//        String pickLocation = (String)session.getAttribute("pickup_location");
//        List<Accessory> accessories = (List<Accessory>)request.getSession().getAttribute("accessories");
//        int[] quantities = (int[])session.getAttribute("quantities");

        reservationsManager.saveReservation((Reservation)session.getAttribute("reservation"), (Customer)session.getAttribute("customer"), (int)session.getAttribute("dropoff_distance"),
                (String)session.getAttribute("dropoff_location"), (int)session.getAttribute("pickup_distance"), (String)session.getAttribute("pickup_location"),
                (int[])session.getAttribute("quantities"), (List<Accessory>)session.getAttribute("accessories"));

        System.out.println("Reservation: " +session.getAttribute("reservation") + "-----------------\n\n");

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
        List<Accessory> accessories = reservationsManager.getAllAccessories();
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
                             @RequestParam("quantities[]") int[] quantities, @RequestParam("status") String reservationStatus) {
        PickUp pickUp = new PickUp(0, pickLocation.trim(), pickDistance);
        DropOff dropOff = new DropOff(0, dropLocation.trim(), dropDistance);
        reservationsManager.updateReservation((Reservation)session.getAttribute("reservation"), reservationStatus,
                (PickUp)session.getAttribute("pickup"), pickUp, (DropOff)session.getAttribute("dropoff"), dropOff, quantities,(Map<Accessory, Integer>)session.getAttribute("accessories"));
        System.out.println(session.getAttribute("reservation"));
        return "redirect:/reservations";
    }

    @GetMapping("/calculate_price")
    public String calculateFinalPrice(@RequestParam("reservation_id") int reservationId, Model model) {
        model.addAttribute("reservation_id", reservationId);
        return "reservations/calculate_price";
    }

    @PostMapping("/calculate_price")
    public String saveFinalPrice(@RequestParam("reservation_id") int reservationId, @RequestParam("total_km") int totalKmDriven, @RequestParam("fuel_fee") boolean addFuelFee) {
        Reservation reservation = reservationsManager.getReservation(reservationId);
        reservationsManager.setFinalPrice(reservation, totalKmDriven, addFuelFee);
        return "redirect:/reservations";
    }

    @GetMapping("/cancel_reservation")
    public String cancelReservation(Model model, @RequestParam("reservation_id") int reservationId) {
        int cancellationFee = reservationsManager.calculateCancellationFee(reservationId);
        model.addAttribute("fee", cancellationFee);
        model.addAttribute("reservation_id", reservationId);
        return "reservations/cancel";
    }

    @PostMapping("/cancel_reservation")
    public String confirmCancellation(@RequestParam("reservation_id") int reservationId) {
        reservationsManager.cancelReservation(reservationId);
        return "redirect:/reservations";
    }

    @GetMapping("/generate_contract")
    public String printContact(Model model, @RequestParam("reservation_id") int reservationId) {
        Reservation reservation = reservationsManager.getReservation(reservationId);
        model.addAttribute("current_date", LocalDate.now());
        model.addAttribute("customer", reservationsManager.getCustomer(reservation.getCustomerId()));
        model.addAttribute("motorhome", reservationsManager.getMotorhome(reservation.getMotorhomeId()));
        Map<Accessory, Integer> accessories = new HashMap<>();
        reservation.getAccessories().forEach((k,v) -> accessories.put(reservationsManager.getAccessory(k),v));
        model.addAttribute("accessories", accessories);
        model.addAttribute("drop_off", reservationsManager.getDropOff(reservationId));
        model.addAttribute("pick_up", reservationsManager.getPickUp(reservationId));
        model.addAttribute("reservation", reservation);
        return "reservations/print_contract";
    }

    @PostMapping("/print_contract")
    public String prepareContract() {
        return "redirect:/reservations";
    }


}