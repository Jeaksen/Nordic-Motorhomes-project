package motorhomes.com.examproject.applicationLogic;

import motorhomes.com.examproject.model.Motorhome;
import motorhomes.com.examproject.model.MotorhomeDescription;
import motorhomes.com.examproject.repositories.MotorhomeDbRepository;
import motorhomes.com.examproject.repositories.MotorhomeDescriptionDbRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Alicja Drankowska
 * todo ?????!!!
 */
public class MotorhomeManager {

    private MotorhomeDbRepository motorhomeDbRepository;
    private MotorhomeDescriptionDbRepository motorhomeDescriptionDbRepository;

    public MotorhomeManager (MotorhomeDbRepository motorhomeDbRepository, MotorhomeDescriptionDbRepository motorhomeDescriptionDbRepository){
        this.motorhomeDbRepository = motorhomeDbRepository;
        this.motorhomeDescriptionDbRepository = motorhomeDescriptionDbRepository;
    }

    public List<Motorhome> getAllMotorhomes(){
        try{
            List<Motorhome> motorhomes = motorhomeDbRepository.readAll();
            return motorhomes;
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error occurred while reading motorhomes' list");
        }
        return null;
    }

    public Motorhome getChosenMotorhome(int motorhomeId){
        try {
            Motorhome motorhome = motorhomeDbRepository.read(motorhomeId);
            return motorhome;
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Motorhome not found!");
        }
        return null;
    }

    public Motorhome addNewMotorhome(String licencePlate){
       Motorhome motorhome = new Motorhome();
       motorhome.setLicencePlate(licencePlate);
       motorhome.setMotorhomeStatus("Available");
       return motorhome;
    }

    public boolean saveMotorhomeDescription(Motorhome motorhome, MotorhomeDescription motorhomeDescription){
        try{
            MotorhomeDescription savedMotorhomeDescription = motorhomeDescriptionDbRepository.read(motorhomeDescription.getMotorhomeDescriptionId());
            if (savedMotorhomeDescription == null) {
                motorhomeDescriptionDbRepository.create(motorhomeDescription);
                savedMotorhomeDescription = motorhomeDescriptionDbRepository.read(motorhomeDescription.getMotorhomeDescriptionId());
            }
            motorhome.setMotorhomeDescription(savedMotorhomeDescription);
            return motorhomeDbRepository.create(motorhome);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //not sure if it will be needed
    public List<Integer> getExistingMotorhomeDescriptionIds(){
        try{
            return motorhomeDescriptionDbRepository.readAllIds();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
/**
    public void addNewModel(MotorhomeDescription motorhomeDescription){
        try {
            motorhomeDescriptionDbRepository.create(motorhomeDescription);
        }catch (SQLException e){
            System.out.println("Error occurred! Model not added!");
            e.printStackTrace();
        }
    }
 */

    public void deleteMotorhome(int motorhomeId){
        try {
            motorhomeDbRepository.delete(motorhomeId);
        }catch (SQLException e){
            System.out.println("Error occurred! Motorhome not deleted");
            e.printStackTrace();
        }
    }
}
