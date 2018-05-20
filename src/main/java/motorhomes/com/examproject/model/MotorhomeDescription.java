package motorhomes.com.examproject.model;

/**
 * @ Alicja Drankowska
 */
public class MotorhomeDescription {

    private int motorhomeDescriptionId;
    private String brand;
    private String model;
    private int basePrice;
    private int capacity;

    public MotorhomeDescription(){

    }

    public MotorhomeDescription(int motorhomeDescriptionId, String brand, String model, int basePrice, int capacity){
        this.motorhomeDescriptionId = motorhomeDescriptionId;
        this.brand = brand;
        this.model = model;
        this.basePrice = basePrice;
        this.capacity = capacity;
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getMotorhomeDescriptionId() {
        return motorhomeDescriptionId;
    }

    public void setMotorhomeDescriptionId(int motorhomeDescriptionId) {
        this.motorhomeDescriptionId = motorhomeDescriptionId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return "MotorhomeDescription{" +
                "motorhomeDescriptionId=" + motorhomeDescriptionId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", basePrice=" + basePrice +
                '}';
    }
}
