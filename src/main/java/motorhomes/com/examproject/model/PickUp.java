package motorhomes.com.examproject.model;

/**
 * @ Alicja Drankowska
 */
public class PickUp {

    private int pickUpId;
    private String pickUpLocation;
    private int pickUpDistance;

    public PickUp(){

    }

    public PickUp(int pickUpId, String pickUpLocation, int pickUpDistance){
        this.pickUpId = pickUpId;
        this.pickUpLocation = pickUpLocation;
        this.pickUpDistance = pickUpDistance;
    }

    public int getPickUpId() {
        return pickUpId;
    }

    public void setPickUpId(int pickUpId) {
        this.pickUpId = pickUpId;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public int getPickUpDistance() {
        return pickUpDistance;
    }

    public void setPickUpDistance(int pickUpDistance) {
        this.pickUpDistance = pickUpDistance;
    }

    @Override
    public String toString() {
        return "PickUp{" +
                "pickUpId=" + pickUpId +
                ", pickUpLocation='" + pickUpLocation + '\'' +
                ", pickUpDistance=" + pickUpDistance +
                '}';
    }
}
