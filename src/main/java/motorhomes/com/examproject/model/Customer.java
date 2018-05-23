package motorhomes.com.examproject.model;

/**
 * @ Alexandra Caragata
 */
public class Customer {

    private int customerId;
    private String customerName;
    private String drivingLicenceNr;

    public Customer() {
    }

    public Customer(int customerId, String customerName, String drivingLicenseNr) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.drivingLicenceNr = drivingLicenseNr;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDrivingLicenceNr() {
        return drivingLicenceNr;
    }

    public void setDrivingLicenceNr(String drivingLicenseNr) {
        this.drivingLicenceNr = drivingLicenseNr;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", drivingLicenseNr='" + drivingLicenceNr + '\'' +
                '}';
    }
}
