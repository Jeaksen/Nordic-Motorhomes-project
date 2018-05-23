package motorhomes.com.examproject.repositories.array;

import motorhomes.com.examproject.model.PickUp;
import motorhomes.com.examproject.repositories.ICrudRepository;

import java.util.ArrayList;

/**
 * @ Alicja Drankowska
 * todo comments
 */
public class PickupArrayListRepository implements ICrudRepository<PickUp> {

    private ArrayList<PickUp> pickUps = new ArrayList<>();

    public PickupArrayListRepository(){
        //add pickUps here
    }

    @Override
    public ArrayList<PickUp> readAll() throws Exception {
        return pickUps;
    }

    @Override
    public boolean create(PickUp pickUp) throws Exception {
        pickUps.add(pickUp);
        pickUp.setPickUpId(pickUps.size());

        return true;
    }

    @Override
    public PickUp read(int id) throws Exception {
        return pickUps.get(id - 1);
    }

    @Override
    public void update(PickUp pickUp) throws Exception {
        for (PickUp pu : pickUps) {
            if (pu.getPickUpId() == pickUp.getPickUpId()){
                pickUps.remove(pu);
                pickUps.add(pickUp);
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        for (PickUp p : pickUps) {
            if (p.getPickUpId() == id){
                pickUps.remove(id - 1);
            }
        }

    }
}
