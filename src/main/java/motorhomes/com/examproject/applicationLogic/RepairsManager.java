package motorhomes.com.examproject.applicationLogic;

import motorhomes.com.examproject.model.Motorhome;
import motorhomes.com.examproject.model.Repair;
import motorhomes.com.examproject.repositories.MotorhomeDbRepository;
import motorhomes.com.examproject.repositories.RepairsDbRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public class RepairsManager {
    private RepairsDbRepository repairsDbRepository;

    @Autowired
    public RepairsManager(RepairsDbRepository repairsDbRepository) {
        this.repairsDbRepository = repairsDbRepository;
    }

    public List<Repair> getRepairs(){
        try {
            List<Repair> repairs=repairsDbRepository.readAll();
            return repairs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createRepair( Repair repair){
        try {
            repairsDbRepository.create(repair);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Repair getRepair(int repairId) {
        try {
            Repair repair= repairsDbRepository.read(repairId);
            return repair;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateRepair(Repair repair){
        try {
            repairsDbRepository.update(repair);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRepair(int repairId){
        try {
            repairsDbRepository.delete(repairId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

