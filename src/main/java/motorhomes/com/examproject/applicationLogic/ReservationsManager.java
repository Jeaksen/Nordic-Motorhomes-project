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
        //todo set status
        return null;
    }

    public List<Motorhome> getAvailableMotorhomes() {
        return null;
    }

    public void saveMotorhome(Reservation reservation, int motorhomesId) {
    }
}
