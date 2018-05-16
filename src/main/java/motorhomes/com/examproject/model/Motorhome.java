package motorhomes.com.examproject.model;

public class Motorhome {

    private int motorhomeId;
    private String licensePlate;
    private String motorhomeStatus;

    private MotorhomeDescription motorhomeDescription;

    public Motorhome(int motorhomeId, String licensePlate, String motorhomeStatus, MotorhomeDescription motorhomeDescription) {
        this.motorhomeId = motorhomeId;
        this.licensePlate = licensePlate;
        this.motorhomeStatus = motorhomeStatus;
        this.motorhomeDescription = motorhomeDescription;
    }

    public Motorhome() {
    }

    public MotorhomeDescription getMotorhomeDescription() {
        return motorhomeDescription;
    }

    public void setMotorhomeDescription(MotorhomeDescription motorhomeDescription) {
        this.motorhomeDescription = motorhomeDescription;
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
        return "Motorhome{" +
                "id=" + motorhomeId +
                ", licensePlate='" + licensePlate + '\'' +
                ", status='" + motorhomeStatus + '\'' +
                '}';
    }


}
