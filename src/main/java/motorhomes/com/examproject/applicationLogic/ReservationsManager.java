package motorhomes.com.examproject.applicationLogic;


import motorhomes.com.examproject.model.*;
import motorhomes.com.examproject.repositories.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Pawel Pohl
 */
public class ReservationsManager {

    private CustomersDbRepository customersRepository;
    private ReservationsRepository reservationsRepository;
    private MotorhomeDbRepository motorhomesRepository;
    private PickupDbRepository pickupDbRepository;
    private DropOffDbRepository dropOffDbRepository;
    private AccessoriesDbRepository accessoriesDbRepository;
    private ReservationsAccessoriesRepository reservationsAccessoriesRepository;

    public ReservationsManager(CustomersDbRepository customersRepository, ReservationsRepository reservationsRepository,
                               MotorhomeDbRepository motorhomesRepository, PickupDbRepository pickupDbRepository,
                               DropOffDbRepository dropOffDbRepository, AccessoriesDbRepository accessoriesDbRepository,
                               ReservationsAccessoriesRepository reservationsAccessoriesRepository) {
        this.customersRepository = customersRepository;
        this.reservationsRepository = reservationsRepository;
        this.motorhomesRepository = motorhomesRepository;
        this.pickupDbRepository = pickupDbRepository;
        this.dropOffDbRepository = dropOffDbRepository;
        this.accessoriesDbRepository = accessoriesDbRepository;
        this.reservationsAccessoriesRepository = reservationsAccessoriesRepository;
    }

    public List<Reservation> getReservations() {
        try {
            List<Reservation> reservations = reservationsRepository.readAll();
            return reservations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Reservation startReservation(LocalDate startDate, LocalDate endDate) {
        Reservation reservation = new Reservation();
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setStatus("Not Payed");
        return reservation;
    }

    public List<Motorhome> getAvailableMotorhomes(Reservation reservation) {
        try {
            List<Integer> availableMotorhomesIds = motorhomesRepository.readAllIDs();
            reservationsRepository.getAvailableMotorhomesIds(reservation.getStartDate(), reservation.getEndDate(), availableMotorhomesIds);
            return motorhomesRepository.readAll(availableMotorhomesIds);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveMotorhome(Reservation reservation, int motorhomesId) {
        reservation.setMotorhomeId(motorhomesId);
        return true;
    }

    public List<Accessory> getAccessories() {
        try {
            return accessoriesDbRepository.readAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveCustomer(Reservation reservation, Customer customer) {
        try {
            Customer savedCustomer = customersRepository.read(customer.getDrivingLicenseNr());
            if (savedCustomer == null) {
                customersRepository.create(customer);
                savedCustomer = customersRepository.read(customer.getDrivingLicenseNr());
            }
            reservation.setCustomerId(savedCustomer.getCustomerId());
            int reservationId = reservationsRepository.create(reservation);
            reservation.setReservationId(reservationId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean saveTransport(Reservation reservation, int dropDistance, String dropLocation, int pickDistance, String pickLocation) {
        try {
            if (dropDistance > 0) {
                DropOff dropOff = new DropOff();
                reservation.setHasDropOff(true);
                dropOff.setDropOffDistance(dropDistance);
                dropOff.setDropOffLocation(dropLocation);
                dropOffDbRepository.create(dropOff, reservation.getReservationId());
            }
                if (pickDistance > 0) {
                PickUp pickUp = new PickUp();
                reservation.setHasPickUp(true);
                pickUp.setPickUpDistance(pickDistance);
                pickUp.setPickUpLocation(pickLocation);
                pickupDbRepository.create(pickUp, reservation.getReservationId());
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAccessories(Reservation reservation, int[] quantities, List<Accessory> accessories) {
        Map<Integer, Integer> accessoryIdToQuantity = new HashMap<>();
        for (int i = 0; i < quantities.length; i++) {
            accessoryIdToQuantity.put(accessories.get(i).getId(), quantities[i]);
            try {
                reservationsAccessoriesRepository.create(reservation.getReservationId(), accessories.get(i).getId(), quantities[i]);
            } catch (SQLException e) {
                return false;
            }
        }
        if (quantities.length > 0){
            reservation.setHasAccessories(true);
        }
        reservation.setAccessories(accessoryIdToQuantity);
        return true;
    }

    public boolean updateReservation(Reservation reservation) {
        try {
            reservationsRepository.update(reservation);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
