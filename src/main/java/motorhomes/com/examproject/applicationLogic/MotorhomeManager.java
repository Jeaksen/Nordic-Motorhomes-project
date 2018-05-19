package motorhomes.com.examproject.applicationLogic;

import motorhomes.com.examproject.model.Motorhome;
import motorhomes.com.examproject.model.MotorhomeDescription;
import motorhomes.com.examproject.repositories.MotorhomeDbRepository;
import motorhomes.com.examproject.repositories.MotorhomeDescriptionDbRepository;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @ Alicja Drankowska
 * todo do logika tej klasy do sprawdzenia
 */
public class MotorhomeManager {

    private MotorhomeDbRepository motorhomeDbRepository;
    private MotorhomeDescriptionDbRepository motorhomeDescriptionDbRepository;

    public MotorhomeManager (MotorhomeDbRepository motorhomeDbRepository, MotorhomeDescriptionDbRepository motorhomeDescriptionDbRepository){
        this.motorhomeDbRepository = motorhomeDbRepository;
        this.motorhomeDescriptionDbRepository = motorhomeDescriptionDbRepository;
    }

    public ArrayList<Motorhome> getAllMotorhomes(){
        try{
            return motorhomeDbRepository.readAll();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error occurred while reading motorhomes' list");
            ArrayList<Motorhome> motorhomes = null;
            return motorhomes;
        }
    }

    public Motorhome getChosenMotorhome(int motorhomeId){
        try {
            return motorhomeDbRepository.read(motorhomeId);
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Motorhome not found!");
            Motorhome motorhome = null;
            return motorhome;
        }
    }

    public void createMotorhome(Motorhome motorhome, int description_id){
        try {
            motorhomeDbRepository.create(motorhome, description_id);
        }catch (SQLException e){
            System.out.println("Motorhome not created!");
            e.printStackTrace();
        }
    }

    public void addNewModel(MotorhomeDescription motorhomeDescription){
        try {
            motorhomeDescriptionDbRepository.create(motorhomeDescription);
        }catch (SQLException e){
            System.out.println("Error occurred! Model not added!");
            e.printStackTrace();
        }
    }

    public void deleteMotorhome(int motorhomeId){
        try {
            motorhomeDbRepository.delete(motorhomeId);
        }catch (SQLException e){
            System.out.println("Error occurred! Motorhome not deleted");
            e.printStackTrace();
        }
    }
}
