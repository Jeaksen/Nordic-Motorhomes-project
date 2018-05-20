package motorhomes.com.examproject.model;

import java.time.LocalDate;
import java.util.Map;

public class Reservation {

    private int reservationId;
    private int customersId;
    private LocalDate  startDate;
    private LocalDate endDate;
    private boolean hasPickUp;
    private boolean hasDropOff;
    private boolean hasAccessories;
    private int price;
    private String reservationStatus;
    private int motorhomeId;
    // Accessory ID and quantity
    private Map  <Integer, Integer> accessories;

    public Reservation() {
        this.hasDropOff = false;
        this.hasPickUp = false;
        this.hasAccessories = false;
        this.price = 0;
    }

    public Map<Integer, Integer> getAccessories() {
        return accessories;
    }

    public void setAccessories(Map<Integer, Integer> accessories) {
        this.accessories = accessories;
    }

    public int getMotorhomeId() {
        return motorhomeId;
    }

    public void setMotorhomeId(int motorhomeId) {
        this.motorhomeId = motorhomeId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getCustomersId() {
        return customersId;
    }

    public void setCustomersId(int customersId) {
        this.customersId = customersId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isHasPickUp() {
        return hasPickUp;
    }

    public void setHasPickUp(boolean hasPickUp) {
        this.hasPickUp = hasPickUp;
    }

    public boolean isHasDropOff() {
        return hasDropOff;
    }

    public void setHasDropOff(boolean hasDropOff) {
        this.hasDropOff = hasDropOff;
    }

    public boolean isHasAccessories() {
        return hasAccessories;
    }

    public void setHasAccessories(boolean hasAccessories) {
        this.hasAccessories = hasAccessories;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", customersID='" + customersId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", hasPickUp=" + hasPickUp +
                ", hasDropOff=" + hasDropOff +
                ", hasAccessories=" + hasAccessories +
                ", price=" + price +
                ", reservationStatus='" + reservationStatus + '\'' +
                ", motorhomeId=" + motorhomeId +
                ", accessories=" + accessories +
                '}';
    }
}
