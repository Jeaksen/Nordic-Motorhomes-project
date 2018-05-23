package motorhomes.com.examproject.repositories.array;

import motorhomes.com.examproject.model.Motorhome;

import java.util.ArrayList;

/**
 * @ Alicja Drankowska
 */
public class MotorhomeArrayListRepository implements ICrudRepository<Motorhome> {

    private ArrayList<Motorhome> motorhomes = new ArrayList<>();

    public MotorhomeArrayListRepository(){
        //add motorhomes here
    }

    @Override
    public ArrayList<Motorhome> readAll() throws Exception {
        return motorhomes;
    }

    @Override
    public boolean create(Motorhome motorhome) throws Exception {
        motorhomes.add(motorhome);
        motorhome.setMotorhomeId(motorhomes.size());

        return true;
    }

    @Override
    public Motorhome read(int id) throws Exception {
        return motorhomes.get(id -1);
    }

    @Override
    public void update(Motorhome motorhome) throws Exception {
        for (Motorhome mh : motorhomes) {
            if (mh.getMotorhomeId() == motorhome.getMotorhomeId()){
                motorhomes.remove(mh);
                motorhomes.add(motorhome);
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        for (Motorhome m : motorhomes) {
            if (m.getMotorhomeId() == id){
                motorhomes.remove(id - 1);
            }
        }
    }

}
