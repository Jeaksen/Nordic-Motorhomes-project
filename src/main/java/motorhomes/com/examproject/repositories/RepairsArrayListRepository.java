package motorhomes.com.examproject.repositories;

import motorhomes.com.examproject.model.Repair;

import java.util.ArrayList;

/**
 * @ Alicja Drankowska
 */
public class RepairsArrayListRepository implements ICrudRepository<Repair> {

    private ArrayList<Repair> repairs = new ArrayList<>();

    public RepairsArrayListRepository(){
        //add repairs here
    }

    @Override
    public ArrayList<Repair> readAll() throws Exception {
        return repairs;
    }

    @Override
    public boolean create(Repair repair) throws Exception {
        repairs.add(repair);
        repair.setRepairId(repairs.size());

        return true;
    }

    @Override
    public Repair read(int id) throws Exception {
        return repairs.get(id - 1);
    }

    @Override
    public void update(Repair repair) throws Exception {
        for (Repair rp : repairs) {
            if (rp.getRepairId() == repair.getRepairId()){
                repairs.remove(rp);
                repairs.add(repair);
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        for (Repair r : repairs) {
            if (r.getRepairId() == id){
                repairs.remove(id - 1);
            }
        }
    }
}
