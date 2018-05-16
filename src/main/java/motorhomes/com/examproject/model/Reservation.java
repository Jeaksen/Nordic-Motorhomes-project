package motorhomes.com.examproject.model;

import java.util.Date;
import java.util.Map;

public class Reservation {

    private int reservationId;
    private String customersName;
    private Date  startDate;
    private Date endDate;
    private boolean hasPickUp;
    private boolean hasDropOff;
    private boolean hasAccessories;
    private int price;
    private String reservationStatus;
    private Map  <Accessory, Integer> accessories;

    public Reservation() {
    }

    public Reservation(int reservationId, String customersName, Date startDate,
                       Date endDate, boolean hasPickUp, boolean hasDropOff,
                       boolean hasAccessories, int price, String reservationStatus) {
        this.reservationId = reservationId;
        this.customersName = customersName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hasPickUp = hasPickUp;
        this.hasDropOff = hasDropOff;
        this.hasAccessories = hasAccessories;
        this.price = price;
        this.reservationStatus = reservationStatus;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getCustomersName() {
        return customersName;
    }

    public void setCustomersName(String customersName) {
        this.customersName = customersName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
                ", customersName='" + customersName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", hasPickUp=" + hasPickUp +
                ", hasDropOff=" + hasDropOff +
                ", hasAccessories=" + hasAccessories +
                ", price=" + price +
                ", status='" + reservationStatus + '\'' +
                '}';
    }


}
