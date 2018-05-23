package motorhomes.com.examproject.applicationLogic;

import motorhomes.com.examproject.model.Motorhome;
import motorhomes.com.examproject.model.MotorhomeDescription;
import motorhomes.com.examproject.repositories.MotorhomeDbRepository;
import motorhomes.com.examproject.repositories.MotorhomeDescriptionDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Alicja Drankowska
 * todo ?????!!!
 */
@Component
public class MotorhomeManager {

    private MotorhomeDbRepository motorhomeDbRepository;
    private MotorhomeDescriptionDbRepository motorhomeDescriptionDbRepository;
    private static final List<String> motorhomeStatuses = new ArrayList<>();

    static {
        motorhomeStatuses.add("Available");
        motorhomeStatuses.add("Rented");
        motorhomeStatuses.add("Before Cleaning");
        motorhomeStatuses.add("Before Service");
    }

    @Autowired
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

    public List<String> getMotorhomeStatuses(){
        return motorhomeStatuses;
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

    public MotorhomeDescription getMotorhomeDescription(int descriptionId) {
        try {
            return motorhomeDescriptionDbRepository.read(descriptionId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Motorhome addNewMotorhome(String licencePlate){
       Motorhome motorhome = new Motorhome();
       motorhome.setLicencePlate(licencePlate);
       motorhome.setMotorhomeStatus("Available");
       return motorhome;
    }

    public boolean saveMotorhomeDescription(Motorhome motorhome, MotorhomeDescription motorhomeDescription){
        if (motorhomeDescription.getMotorhomeDescriptionId() > 0){
            motorhome.setMotorhomeDescription(motorhomeDescription);
        }else {
            try {
                motorhomeDescription.setMotorhomeDescriptionId(motorhomeDescriptionDbRepository.create(motorhomeDescription));
                motorhome.setMotorhomeDescription(motorhomeDescription);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public List<MotorhomeDescription> getExistingMotorhomeDescriptions (){
        try{
            return motorhomeDescriptionDbRepository.readAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public MotorhomeDescription addNewModel (String brand, String model, int capacity, int base_price){
        MotorhomeDescription motorhomeDescription = new MotorhomeDescription();
        motorhomeDescription.setBrand(brand);
        motorhomeDescription.setModel(model);
        motorhomeDescription.setCapacity(capacity);
        motorhomeDescription.setBasePrice(base_price);
        return motorhomeDescription;
    }

    public boolean saveNewMotorhome(Motorhome motorhome, MotorhomeDescription motorhomeDescription){
        if (!this.saveMotorhomeDescription(motorhome, motorhomeDescription)) return false;
        try {
            motorhomeDbRepository.create(motorhome);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void updateMotorhome(Motorhome motorhome){
        try {
            motorhomeDbRepository.update(motorhome);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
