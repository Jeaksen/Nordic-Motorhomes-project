package motorhomes.com.examproject.model;

public class Accessory {

    private int accessoryId;
    private String accessoryName;

    private int accessoryPrice;


    public Accessory(){

    }

    public Accessory(int accessoryId, String accessoryName, int accessoryPrice){
        this.accessoryId = accessoryId;
        this.accessoryName = accessoryName;
        this.accessoryPrice = accessoryPrice;
    }

    public int getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(int accessoryId) {
        this.accessoryId = accessoryId;
    }

    public String getAccessoryName() {
        return accessoryName;
    }

    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName;
    }

    public int getAccessoryPrice() {
        return accessoryPrice;
    }

    public void setAccessoryPrice(int accessoryPrice) {
        this.accessoryPrice = accessoryPrice;
    }


    @Override
    public String toString() {
        return "Accessory{" +
                "accessoryId=" + accessoryId +
                ", accessoryName='" + accessoryName + '\'' +
                ", accessoryPrice=" + accessoryPrice +
                '}';
    }


}
