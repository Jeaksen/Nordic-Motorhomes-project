package motorhomes.com.examproject.model;

import java.util.Date;

public class Reservations {

    private int reservationId;
    private String customersName;
    private Date  startDate;
    private Date endDate;
    private boolean pickUp;
    private boolean dropOff;
    private boolean accessories;
    private int price;
    private String reservationStatus;

    public Reservations() {
    }

    public Reservations(int reservationId, String customersName, Date startDate,
                        Date endDate, boolean pickUp, boolean dropOff,
                        boolean accessories, int price, String reservationStatus) {
        this.reservationId = reservationId;
        this.customersName = customersName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickUp = pickUp;
        this.dropOff = dropOff;
        this.accessories = accessories;
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

    public boolean isPickUp() {
        return pickUp;
    }

    public void setPickUp(boolean pickUp) {
        this.pickUp = pickUp;
    }

    public boolean isDropOff() {
        return dropOff;
    }

    public void setDropOff(boolean dropOff) {
        this.dropOff = dropOff;
    }

    public boolean isAccessories() {
        return accessories;
    }

    public void setAccessories(boolean accessories) {
        this.accessories = accessories;
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
        return "Reservations{" +
                "reservationId=" + reservationId +
                ", customersName='" + customersName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", pickUp=" + pickUp +
                ", dropOff=" + dropOff +
                ", accessories=" + accessories +
                ", price=" + price +
                ", status='" + reservationStatus + '\'' +
                '}';
    }


}
