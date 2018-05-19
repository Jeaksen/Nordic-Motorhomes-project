package motorhomes.com.examproject.model;

public class Motorhome {

    private int motorhomeId;
    private String licencePlate;
    private String motorhomeStatus;
    private MotorhomeDescription motorhomeDescription;

    public Motorhome(int motorhomeId, String licencePlate, String motorhomeStatus, MotorhomeDescription motorhomeDescription) {
        this.motorhomeId = motorhomeId;
        this.licencePlate = licencePlate;
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

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
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
                ", licencePlate='" + licencePlate + '\'' +
                ", status='" + motorhomeStatus + '\'' +
                '}';
    }


}
