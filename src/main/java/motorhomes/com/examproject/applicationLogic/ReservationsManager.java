package motorhomes.com.examproject.applicationLogic;


import motorhomes.com.examproject.model.Customer;
import motorhomes.com.examproject.model.Motorhome;
import motorhomes.com.examproject.model.Reservation;
import motorhomes.com.examproject.repositories.CustomersDbRepository;

import java.time.LocalDate;
import java.util.List;

public class ReservationsManager {

    private CustomersDbRepository customersRepository;

    public ReservationsManager(CustomersDbRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public void setCustomersRepository(CustomersDbRepository customersRepository){
        this.customersRepository = customersRepository;
    }

    public List<Motorhome> getReservations() {
        return null;
    }

    public Reservation startReservation(LocalDate startDate, LocalDate endDate) {
        System.out.println(startDate);
        System.out.println(endDate);
        Reservation reservation = new Reservation();
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        //todo set status
        return reservation;
    }

    public List<Motorhome> getAvailableMotorhomes() {
        return null;
    }

    public void saveMotorhome(Reservation reservation, int motorhomesId) {
        reservation.setMotorhomeId(motorhomesId);
    }

    public void saveCustomer(Reservation reservation, Customer customer) {
        reservation.setCustomersName(customer.getCustomerName());
    }

    public void saveTransport(Reservation reservation, int dropDistance, String dropLocation, int pickDistance, String pickLocation) {
        reservation.setHasDropOff(dropDistance > 0);
        reservation.setHasPickUp(pickDistance > 0);
    }

    public void saveAccessories(Reservation reservation, int[] quantities) {
        for (int quantity :
                quantities) {
            if (quantity > 0) {
                reservation.setHasAccessories(true);
                break;
            }
        }
    }
}
