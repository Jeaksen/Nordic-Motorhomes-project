package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.Accessory;

import java.util.ArrayList;

public class AccessoryArrayListRepository implements ICrudRepository<Accessory>{

    private ArrayList<Accessory> accessories = new ArrayList<>();


    public AccessoryArrayListRepository(){

        //add more accessories here?
        accessories.add(new Accessory(1, "child seat", 100 ));
        accessories.add(new Accessory(2, "bike rack", 100));
        accessories.add(new Accessory(3, "bed linen", 50));
        accessories.add(new Accessory(4, "picnic table", 50));
        accessories.add(new Accessory(5, "chairs", 50 ));
    }

    @Override
    public ArrayList<Accessory> readAll() {
        //code reading from an ArrayList
        return accessories;
    }

    @Override
    public boolean create(Accessory accessory) {
        //code adding from an ArrayList
        accessories.add(accessory);
        accessory.setAccessoryId(accessories.size());

        return true;
    }

    @Override
    public Accessory read(int id) {
        return accessories.get(id - 1);
    }

    @Override
    public void update(Accessory accessory) {
        for (Accessory ac : accessories) {

            if (ac.getAccessoryId() == accessory.getAccessoryId()){
                accessories.remove(ac);
                accessories.add(accessory);
            }
        }
    }

    @Override
    public void delete(int id) {

        for (Accessory a: accessories) {
            if (a.getAccessoryId() == id){
                accessories.remove(id - 1);
            }
        }

    }


}
