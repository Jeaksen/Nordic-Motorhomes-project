package motorhomes.com.examproject.controller;

import motorhomes.com.examproject.applicationLogic.MotorhomeManager;
import motorhomes.com.examproject.model.Motorhome;
import motorhomes.com.examproject.repositories.MotorhomeDbRepository;
import motorhomes.com.examproject.repositories.MotorhomeDescriptionDbRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

/**
 * @ Alicja Drankowska
 * todo do metoda post do sprawdzenia
 */
@Controller
public class MotorhomeController {

    private MotorhomeManager motorhomeManager;

    public MotorhomeController() throws SQLException{
        this.motorhomeManager = new MotorhomeManager(new MotorhomeDbRepository(), new MotorhomeDescriptionDbRepository());
    }

    @GetMapping("/newmotorhome")
    public String newmotorhome(){
        return "newmotorhome";
    }

    @PostMapping("/newmotorhome")
    public String newmotorhome(@RequestParam("description_id") int descriptionId, @ModelAttribute Motorhome motorhome){
        motorhomeManager.createMotorhome(motorhome,descriptionId );
        return "redirect:/fleet";
    }
    @GetMapping("/fleet")
    public String fleet(){
        return"fleet";
    }
}
