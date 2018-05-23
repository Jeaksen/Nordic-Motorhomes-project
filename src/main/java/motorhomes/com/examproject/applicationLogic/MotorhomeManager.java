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

    public boolean saveMotorhomeDescription(Motorhome motorhome, int motorhomeDescriptionId){
        try {
            motorhome.setMotorhomeDescription(motorhomeDescriptionDbRepository.read(motorhomeDescriptionId));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    /**public boolean saveMotorhomeDescription(Motorhome motorhome, MotorhomeDescription motorhomeDescription){
        try{
            /*Nie wiem jak to zaplanowalas ale czy motorhomeDescription.getMotorhomeDescriptionId() nie zwraca czasem bledu ze nie zostal zinicjalizowany jesli towrzysz nowe description?
           Jesli tak to napisz i ci powiem jak temu zapobiec


            MotorhomeDescription savedMotorhomeDescription = motorhomeDescriptionDbRepository.read(motorhomeDescription.getMotorhomeDescriptionId());
            if (savedMotorhomeDescription == null) {
                motorhomeDescriptionDbRepository.create(motorhomeDescription);
                savedMotorhomeDescription = motorhomeDescriptionDbRepository.read(motorhomeDescription.getMotorhomeDescriptionId());
            }
            motorhome.setMotorhomeDescription(savedMotorhomeDescription);
            motorhomeDbRepository.create(motorhome);
        }catch (SQLException e){
            e.printStackTrace();
            return false;

        }
        return true;
    }*/

            // W tym miejscu ja zwalilem sprawe, jesli pojdziesz do MotorhomeDBRepository to statement.execute() nie zwraca
            // true w momencie w ktorym polecenie zostalo wykonane, tylko w momencie w ktorym zapytanie do bazy danych zwraca jakis wynik(ResultSet).
            // Czyli kiedy towrzysz nowy wiersz to ta funckja bedzie zwraca false, bo zapytanie "INSERT INTO" nie zwraca zadnego wyniku.
            // Niestyty nie wiedzialem tego wczesniej. Co proponuje to w zamiast zwracac to co zwraca ta funkcja zmienic kod tak:
            /*
                 motorhomeDbRepository.create(motorhome);
            }catch (SQLException e){
                 e.printStackTrace();
                return false;
            }
            return true;
    }
             */
            //W tym momencie jezeli funckja motorhomeDbRepository.create(motorhome) wyrzuci SQLException to zwracasz false,
            // a jesli wszystko pojdzie zgodnie z planem i ta funckja dojdzie do konca to zwracasz true
            // To samo tyczy sie innych funkcji z baz danych ktore zwracaja to co statement.execute()
            // Zajme sie zmienianiem tego wszystkiego, a Ty po prostu tego nie uzywaj, udawaj ze nie istnieje :D

    //not sure if it will be needed
    public List<MotorhomeDescription> getExistingMotorhomeDescriptions (Motorhome motorhome){
        try{
            motorhomeDescriptionDbRepository.readAll();
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
