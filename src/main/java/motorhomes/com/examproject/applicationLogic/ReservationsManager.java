package motorhomes.com.examproject.applicationLogic;


import motorhomes.com.examproject.model.*;
import motorhomes.com.examproject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Pawel Pohl
 */

@Component
public class ReservationsManager {

    private CustomersDbRepository customersRepository;
    private ReservationsRepository reservationsRepository;
    private MotorhomeDbRepository motorhomesRepository;
    private PickupDbRepository pickupDbRepository;
    private DropOffDbRepository dropOffDbRepository;
    private AccessoriesDbRepository accessoriesDbRepository;
    private ReservationsAccessoriesRepository reservationsAccessoriesRepository;
    private final static double TRANSPORT_FEE_PER_KM = 0.7;
    private final static int FUEL_FEE = 70;
    private final static int EXTRA_KM_FEE = 1;
    private final static int KM_PER_DAY_LIMIT = 400;

    @Autowired
    public ReservationsManager(@Qualifier("getCustomersDbRepository") CustomersDbRepository customersRepository, ReservationsRepository reservationsRepository, @Qualifier("getMotorhomeDbRepository") MotorhomeDbRepository motorhomesRepository, PickupDbRepository pickupDbRepository,
                               DropOffDbRepository dropOffDbRepository, AccessoriesDbRepository accessoriesDbRepository, ReservationsAccessoriesRepository reservationsAccessoriesRepository) {

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
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * This method creates a Reservation instance and sets its startDate, endDate and status
     * @param startDate start date of reservation
     * @param endDate end date of reservation
     * @return reservation object or null if dates are invalid
     */
    public Reservation startReservation(LocalDate startDate, LocalDate endDate) {
        Reservation reservation = new Reservation();
        if (Period.between(startDate,endDate).getDays() <= 0 || Period.between(LocalDate.now(), startDate).getDays() <= 0) {
            return null;
        }
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

    public boolean saveReservation(Reservation reservation, Customer customer, int dropDistance, String dropLocation, int pickDistance, String pickLocation, int[] quantities, List<Accessory> accessories) {

        if(!this.saveCustomer(reservation, customer)) return false;
        if(!this.saveTransport(reservation, dropDistance, dropLocation, pickDistance, pickLocation)) return false;
        if(!this.saveAccessories(reservation, quantities, accessories)) return false;
        if(!this.updatePrice(reservation)) return false;
        if(!this.uploadReservation(reservation)) return false;
        return true;
    }

    private boolean saveCustomer(Reservation reservation, Customer customer) {
        try {
            Customer savedCustomer = customersRepository.read(customer.getDrivingLicenceNr());
            if (savedCustomer == null) {
                customersRepository.create(customer);
                savedCustomer = customersRepository.read(customer.getDrivingLicenceNr());
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

    private boolean saveTransport(Reservation reservation, int dropDistance, String dropLocation, int pickDistance, String pickLocation) {
        try {
            if (dropDistance > 0) {
                DropOff dropOff = new DropOff();
                reservation.setHasDropOff(true);
                dropOff.setDropOffDistance(dropDistance);
                dropOff.setDropOffLocation(dropLocation.trim());
                dropOffDbRepository.create(dropOff, reservation.getReservationId());
            }
                if (pickDistance > 0) {
                PickUp pickUp = new PickUp();
                reservation.setHasPickUp(true);
                pickUp.setPickUpDistance(pickDistance);
                pickUp.setPickUpLocation(pickLocation.trim());
                pickupDbRepository.create(pickUp, reservation.getReservationId());
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean saveAccessories(Reservation reservation, int[] quantities, List<Accessory> accessories) {
        Map<Integer, Integer> accessoryIdToQuantity = new HashMap<>();
        for (int i = 0; i < quantities.length; i++) {
            if (quantities[i] > 0) {
                accessoryIdToQuantity.put(accessories.get(i).getId(), quantities[i]);
                try {
                    reservationsAccessoriesRepository.create(reservation.getReservationId(), accessories.get(i).getId(), quantities[i]);
                } catch (SQLException e) {
                    return false;
                }
                if (!reservation.isHasAccessories()) {
                    reservation.setHasAccessories(true);
                }
            }

        }
        reservation.setAccessories(accessoryIdToQuantity);
        return true;
    }

    private boolean uploadReservation(Reservation reservation) {
        try {
            reservationsRepository.update(reservation);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateReservation(Reservation reservation, String newStatus, PickUp oldPickUp, PickUp newPickUp, DropOff oldDropOff, DropOff newDropOff, int[] newQuantities, Map<Accessory, Integer> accessoryQuantityMap) {

        boolean updatePrice = false;
        if (!reservation.getStatus().equals(newStatus)) {
            reservation.setStatus(newStatus);
        }
        try {

            if (newPickUp.getPickUpDistance() > 0) {
                if (oldPickUp.getPickUpDistance() > 0) {
                    if (oldPickUp.getPickUpDistance() != newPickUp.getPickUpDistance() || !oldPickUp.getPickUpLocation().equals(newPickUp.getPickUpLocation())) {
                        newPickUp.setPickUpId(oldPickUp.getPickUpId());
                        pickupDbRepository.update(newPickUp);
                        updatePrice = true;
                    }
                } else {
                    pickupDbRepository.create(newPickUp, reservation.getReservationId());
                    reservation.setHasPickUp(true);
                    updatePrice = true;
                }
            } else {
                if (oldPickUp.getPickUpDistance() > 0) {
                    pickupDbRepository.delete(oldPickUp.getPickUpId());
                    reservation.setHasPickUp(false);
                    updatePrice = true;
                }
            }

            if (newDropOff.getDropOffDistance() > 0) {
                if (oldDropOff.getDropOffDistance() > 0) {
                    if (oldDropOff.getDropOffDistance() != newDropOff.getDropOffDistance() || !oldDropOff.getDropOffLocation().equals(newDropOff.getDropOffLocation())) {
                        newDropOff.setDropOffId(oldDropOff.getDropOffId());
                        dropOffDbRepository.update(newDropOff);
                        updatePrice = true;
                    }
                } else {
                    reservation.setHasDropOff(true);
                    dropOffDbRepository.create(newDropOff, reservation.getReservationId());
                    updatePrice = true;
                }
            } else {
                if (oldDropOff.getDropOffDistance() > 0) {
                    reservation.setHasDropOff(false);
                    dropOffDbRepository.delete(oldDropOff.getDropOffId());
                    updatePrice = true;
                }
            }

            int i = 0;
            boolean hasAccessories = false;
            for (Map.Entry<Accessory, Integer> entry : accessoryQuantityMap.entrySet()) {
                if (entry.getValue() == 0) {
                    if (newQuantities[i] > 0) {
                        reservationsAccessoriesRepository.create(reservation.getReservationId(), entry.getKey().getId(), newQuantities[i]);
                        updatePrice = true;
                    }
                } else {
                    if (newQuantities[i] == 0) {
                        reservationsAccessoriesRepository.delete(reservation.getReservationId(), entry.getKey().getId());
                        updatePrice = true;
                    } else {
                        reservationsAccessoriesRepository.update(reservation.getReservationId(), entry.getKey().getId(), newQuantities[i]);
                        updatePrice = true;
                    }
                }
                if (newQuantities[i] > 0) {
                    hasAccessories = true;
                }
                i++;
            }
            reservation.setHasAccessories(hasAccessories);
            if (updatePrice) {
                this.updatePrice(reservation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return this.uploadReservation(reservation);
    }

    private boolean updatePrice(Reservation reservation) {
    try {
        double price = 0;
        Map<Integer,Integer> accessoryIdQuantityMap = reservationsAccessoriesRepository.readAll(reservation.getReservationId());
        DropOff dropOff = dropOffDbRepository.read(reservation.getReservationId());
        PickUp pickUp = pickupDbRepository.read(reservation.getReservationId());

        price += motorhomesRepository.read(reservation.getMotorhomeId()).getMotorhomeDescription().getBasePrice();
        price *= this.getPriceMultiplier(reservation);
        if (dropOff != null) {
            price += (double)dropOff.getDropOffDistance() * ReservationsManager.TRANSPORT_FEE_PER_KM;
        }
        if (pickUp != null) {
            price += (double)pickUp.getPickUpDistance() * ReservationsManager.TRANSPORT_FEE_PER_KM;
        }
        for (Map.Entry<Integer, Integer> entry : accessoryIdQuantityMap.entrySet()) {
            price += accessoriesDbRepository.read(entry.getKey()).getPrice() * entry.getValue();
        }
        reservation.setPrice((int)Math.round(price));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private double getPriceMultiplier(Reservation reservation) {
        LocalDate startDate = reservation.getStartDate();
        if (startDate.getMonth().compareTo(SeasonMultiplier.PEAK.getStartDate().getMonth()) >= 0) {
            if (startDate.getMonth().compareTo(SeasonMultiplier.PEAK.getEndDate().getMonth()) <= 0){
                return SeasonMultiplier.PEAK.getMultiplier();
            } else if (startDate.getMonth().compareTo(SeasonMultiplier.MEDIUM.getStartDate().getMonth()) >= 0) {
                return SeasonMultiplier.MEDIUM.getMultiplier();
            }
        } else if (startDate.getMonth().compareTo(SeasonMultiplier.MEDIUM.getStartDate().getMonth()) >= 0) {
            return SeasonMultiplier.MEDIUM.getMultiplier();
        }
        return SeasonMultiplier.LOW.getMultiplier();
    }

    public boolean setFinalPrice (Reservation reservation, int totalKm, boolean addFuelFee) {
        double price = reservation.getPrice();
        int extraKm;
        int reservationLength = Period.between(reservation.getStartDate(), reservation.getEndDate()).getDays();
        extraKm = totalKm - reservationLength * ReservationsManager.KM_PER_DAY_LIMIT;
        extraKm = extraKm < 0? 0 : extraKm;
        price += (double)extraKm * ReservationsManager.EXTRA_KM_FEE;
        if (addFuelFee) {
            price += ReservationsManager.FUEL_FEE;
        }
        reservation.setPrice((int)Math.round(price));
        reservation.setStatus("Payed");
        return this.uploadReservation(reservation);

    }

    public int calculateCancellationFee(int reservationId) {
        Reservation reservation = this.getReservation(reservationId);
        double cancellationFee;
        int daysToReservation = Period.between(LocalDate.now(), reservation.getStartDate()).getDays();
        if (daysToReservation >= 50) {
            cancellationFee = (double)reservation.getPrice() * 0.2;
            cancellationFee = cancellationFee<200? 200 : cancellationFee;
        } else if (daysToReservation >= 15) {
            cancellationFee = (double)reservation.getPrice() * 0.5;
        } else if (daysToReservation > 1) {
            cancellationFee = (double)reservation.getPrice() * 0.8;
        } else {
            cancellationFee = (double)reservation.getPrice() * 0.95;
        }
        return (int)Math.round(cancellationFee);
    }

    public boolean cancelReservation(int reservationId) {
        Reservation reservation = this.getReservation(reservationId);
        if (reservation != null) {
            reservation.setStatus("Canceled");
            return this.uploadReservation(reservation);
        }
        return false;
    }

    public List<Accessory> getAllAccessories() {
        try {
            return accessoriesDbRepository.readAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Reservation getReservation(int reservationId) {
        try {
            Reservation reservation;
            reservation = reservationsRepository.read(reservationId);
            reservation.setAccessories(reservationsAccessoriesRepository.readAll(reservation.getReservationId()));
            return reservation;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DropOff getDropOff (int reservationId) {
        try {
            return dropOffDbRepository.read(reservationId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PickUp getPickUp (int reservationId) {
        try {
            return pickupDbRepository.read(reservationId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Customer getCustomer (int customerId) {
        try {
            return customersRepository.read(customerId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Motorhome getMotorhome (int motorhomeId) {
        try {
            return motorhomesRepository.read(motorhomeId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Accessory getAccessory(int accessoryId) {
        try {
            return accessoriesDbRepository.read(accessoryId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void changeMotorhomeStatus(int motorhomeId, String status) {
        Motorhome motorhome = getMotorhome(motorhomeId);
        switch (status.toLowerCase().trim()) {
            case "rented": motorhome.setMotorhomeStatus("Rented"); break;
            case "returned": motorhome.setMotorhomeStatus("Before Cleaning");
        }
        try {
            motorhomesRepository.update(motorhome);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
