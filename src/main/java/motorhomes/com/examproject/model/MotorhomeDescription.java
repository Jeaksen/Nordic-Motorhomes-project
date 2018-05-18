package motorhomes.com.examproject.model;

public class MotorhomeDescription {

    private int motorhomeDescriptionId;

    private String motorhomeBrand;
    private String motorhomeModel;
    private int motorhomeBasePrice;

    public MotorhomeDescription(){

    }

    public MotorhomeDescription(int motorhomeDescriptionId, String motorhomeBrand, String motorhomeModel, int motorhomeBasePrice){
        this.motorhomeDescriptionId = motorhomeDescriptionId;
        this.motorhomeBrand = motorhomeBrand;
        this.motorhomeModel = motorhomeModel;
        this.motorhomeBasePrice = motorhomeBasePrice;
    }

    public int getMotorhomeDescriptionId() {
        return motorhomeDescriptionId;
    }

    public void setMotorhomeDescriptionId(int motorhomeDescriptionId) {
        this.motorhomeDescriptionId = motorhomeDescriptionId;
    }

    public String getMotorhomeBrand() {
        return motorhomeBrand;
    }

    public void setMotorhomeBrand(String motorhomeBrand) {
        this.motorhomeBrand = motorhomeBrand;
    }

    public String getMotorhomeModel() {
        return motorhomeModel;
    }

    public void setMotorhomeModel(String motorhomeModel) {
        this.motorhomeModel = motorhomeModel;
    }

    public int getMotorhomeBasePrice() {
        return motorhomeBasePrice;
    }

    public void setMotorhomeBasePrice(int motorhomeBasePrice) {
        this.motorhomeBasePrice = motorhomeBasePrice;
    }

    @Override
    public String toString() {
        return "MotorhomeDescription{" +
                "motorhomeDescriptionId=" + motorhomeDescriptionId +
                ", motorhomeBrand='" + motorhomeBrand + '\'' +
                ", motorhomeModel='" + motorhomeModel + '\'' +
                ", motorhomeBasePrice=" + motorhomeBasePrice +
                '}';
    }
}
