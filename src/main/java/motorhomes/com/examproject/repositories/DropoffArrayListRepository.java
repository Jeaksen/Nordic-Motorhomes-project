package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.DropOff;

import java.util.ArrayList;

/**
 * @ Alicja Drankowska
 */
public class DropoffArrayListRepository implements ICrudRepository<DropOff> {

    private ArrayList<DropOff> dropOffs = new ArrayList<>();

    public DropoffArrayListRepository(){
        //add drop-offs here
    }

    @Override
    public ArrayList<DropOff> readAll() throws Exception {
        return dropOffs;
    }

    @Override
    public boolean create(DropOff dropOff) throws Exception {
        dropOffs.add(dropOff);
        dropOff.setDropOffId(dropOffs.size());

        return true;
    }

    @Override
    public DropOff read(int id) throws Exception {
        return dropOffs.get(id - 1);
    }

    @Override
    public void update(DropOff dropOff) throws Exception {
        for (DropOff df : dropOffs) {
            if (df.getDropOffId() == dropOff.getDropOffId()){
                dropOffs.remove(df);
                dropOffs.add(dropOff);
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        for (DropOff d :dropOffs) {
            if (d.getDropOffId() == id){
                dropOffs.remove(id - 1);
            }
        }
    }
}
