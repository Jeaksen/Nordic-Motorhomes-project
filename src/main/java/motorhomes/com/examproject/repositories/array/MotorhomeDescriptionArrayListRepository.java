package motorhomes.com.examproject.repositories.array;

import motorhomes.com.examproject.model.MotorhomeDescription;
import motorhomes.com.examproject.repositories.ICrudRepository;

import java.util.ArrayList;

/**
 * @ Alicja Drankowska
 */
public class MotorhomeDescriptionArrayListRepository implements ICrudRepository<MotorhomeDescription> {

    private ArrayList<MotorhomeDescription> motorhomeDescriptions = new ArrayList<>();

    public MotorhomeDescriptionArrayListRepository(){

        //add motorhomeDescriptions here
    }


    @Override
    public ArrayList<MotorhomeDescription> readAll() throws Exception {

        return motorhomeDescriptions;
    }

    @Override
    public boolean create(MotorhomeDescription motorhomeDescription) throws Exception {

        motorhomeDescriptions.add(motorhomeDescription);
        motorhomeDescription.setMotorhomeDescriptionId(motorhomeDescriptions.size());

        return true;
    }

    @Override
    public MotorhomeDescription read(int id) throws Exception {

        return motorhomeDescriptions.get(id - 1);
    }

    @Override
    public void update(MotorhomeDescription motorhomeDescription) throws Exception {

        for (MotorhomeDescription md : motorhomeDescriptions) {

            if (md.getMotorhomeDescriptionId() == motorhomeDescription.getMotorhomeDescriptionId()){
                motorhomeDescriptions.remove(md);
                motorhomeDescriptions.add(motorhomeDescription);
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {

        for (MotorhomeDescription m : motorhomeDescriptions) {
            if (m.getMotorhomeDescriptionId() == id){
                motorhomeDescriptions.remove(id -1);
            }
        }
    }

}
