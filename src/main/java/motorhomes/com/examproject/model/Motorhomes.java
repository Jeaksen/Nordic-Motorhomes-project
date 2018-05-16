package motorhomes.com.examproject.model;

public class Motorhomes {

    private int motorhomeId;
    private String licensePlate;
    private String motorhomeStatus;

    public Motorhomes(int motorhomeId, String licensePlate, String motorhomeStatus) {
        this.motorhomeId = motorhomeId;
        this.licensePlate = licensePlate;
        this.motorhomeStatus = motorhomeStatus;
    }

    public Motorhomes() {
    }

    public int getMotorhomeId() {
        return motorhomeId;
    }

    public void setMotorhomeId(int motorhomeId) {
        this.motorhomeId = motorhomeId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getMotorhomeStatus() {
        return motorhomeStatus;
    }

    public void setMotorhomeStatus(String motorhomeStatus) {
        this.motorhomeStatus = motorhomeStatus;
    }


    @Override
    public String toString() {
        return "Motorhomes{" +
                "id=" + motorhomeId +
                ", licensePlate='" + licensePlate + '\'' +
                ", status='" + motorhomeStatus + '\'' +
                '}';
    }


}
