package motorhomes.com.examproject.repositories.array;

import motorhomes.com.examproject.model.Accessory;

import java.util.ArrayList;

/**
 * @ Alicja Drankowska
 */
public class AccessoriesArrayListRepository implements ICrudRepository<Accessory> {

    private ArrayList<Accessory> accessories = new ArrayList<>();


    public AccessoriesArrayListRepository(){

        //add more accessories?
        accessories.add(new Accessory(1, "child seat", 100 ));
        accessories.add(new Accessory(2, "bike rack", 100));
        accessories.add(new Accessory(3, "bed linen", 50));
        accessories.add(new Accessory(4, "picnic table", 50));
        accessories.add(new Accessory(5, "chairs", 50 ));
    }

    @Override
    public ArrayList<Accessory> readAll() throws Exception {
        //code reading from an ArrayList
        return accessories;
    }

    @Override
    public boolean create(Accessory accessory) throws Exception {
        //code adding from an ArrayList
        accessories.add(accessory);
        accessory.setId(accessories.size());

        return true;
    }

    @Override
    public Accessory read(int id) throws Exception {
        return accessories.get(id - 1);
    }

    @Override
    public void update(Accessory accessory) throws Exception{
        for (Accessory ac : accessories) {

            if (ac.getId() == accessory.getId()){
                accessories.remove(ac);
                accessories.add(accessory);
            }
        }
    }

    @Override
    public void delete(int id) throws Exception{

        for (Accessory a: accessories) {
            if (a.getId() == id){
                accessories.remove(id - 1);
            }
        }
    }


}
