package motorhomes.com.examproject.model;

/**
 * @ Alicja Drankowska
 */
public class DropOff {

    private int dropOffId;
    private String dropOffLocation;
    private int dropOffDistance;

    public DropOff(){

    }

    public DropOff(int dropOffId, String dropOffLocation, int dropOffDistance){
        this.dropOffId = dropOffId;
        this.dropOffLocation = dropOffLocation;
        this.dropOffDistance = dropOffDistance;
    }

    public int getDropOffId() {
        return dropOffId;
    }

    public void setDropOffId(int dropOffId) {
        this.dropOffId = dropOffId;
    }

    public String getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public int getDropOffDistance() {
        return dropOffDistance;
    }

    public void setDropOffDistance(int dropOffDistance) {
        this.dropOffDistance = dropOffDistance;
    }

    @Override
    public String toString() {
        return "DropOff{" +
                "dropOffId=" + dropOffId +
                ", dropOffLocation='" + dropOffLocation + '\'' +
                ", dropOffDistance=" + dropOffDistance +
                '}';
    }
}
