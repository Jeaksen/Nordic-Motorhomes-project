package motorhomes.com.examproject.model;

public class Customer {

    private int customerId;
    private String customerName;
    private String drivingLicenseNr;

    public Customer() {
    }

    public Customer(int customerId, String customerName, String drivingLicenseNr) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.drivingLicenseNr = drivingLicenseNr;
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

    public String getDrivingLicenseNr() {
        return drivingLicenseNr;
    }

    public void setDrivingLicenseNr(String drivingLicenseNr) {
        this.drivingLicenseNr = drivingLicenseNr;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", drivingLicenseNr='" + drivingLicenseNr + '\'' +
                '}';
    }
}
