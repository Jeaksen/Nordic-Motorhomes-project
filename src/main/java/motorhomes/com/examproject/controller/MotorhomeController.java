package motorhomes.com.examproject.controller;

import motorhomes.com.examproject.applicationLogic.MotorhomeManager;
import motorhomes.com.examproject.model.Motorhome;
import motorhomes.com.examproject.model.MotorhomeDescription;
import motorhomes.com.examproject.repositories.MotorhomeDbRepository;
import motorhomes.com.examproject.repositories.MotorhomeDescriptionDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * @ Alicja Drankowska
 * todo confirmPOST method and comment!
 */
@Controller
public class MotorhomeController {

    private MotorhomeManager motorhomeManager;

    @Autowired
    public MotorhomeController(MotorhomeManager motorhomeManager) throws SQLException{
        this.motorhomeManager = motorhomeManager;
    }

    @GetMapping("/fleet")
    public String getAllMotorhomes(Model model){
        List<Motorhome> motorhomes = motorhomeManager.getAllMotorhomes();
        model.addAttribute("motorhomes", motorhomes);
        return"fleet";
    }

    @GetMapping("/add_motorhome")
    public String addNewMotorhome(){
        return "add_motorhome";
    }

    @PostMapping("/add_motorhome")
    public String addNewMotorhome(@RequestParam("licence_plate") String licencePlate, HttpServletRequest request, Model model){
        Motorhome motorhome = motorhomeManager.addNewMotorhome(licencePlate);
        request.getSession(true).setAttribute("motorhome", motorhome);
        List<MotorhomeDescription> motorhomeDescriptions;
        motorhomeDescriptions = motorhomeManager.getExistingMotorhomeDescriptions(motorhome);
        model.addAttribute("motorhomeDescriptions", motorhomeDescriptions);
        return "/create_description";
    }

    @PostMapping("/create_description")
    public String addNewModel (HttpServletRequest request,
        @RequestParam(value = "brand", required = false) String brand, @RequestParam(value = "model", required = false) String model,
        @RequestParam(value = "capacity", required = false) int capacity, @RequestParam(value = "base_price", required = false) int base_price){

        request.getSession().setAttribute("brand", brand);
        request.getSession().setAttribute("model", model);
        request.getSession().setAttribute("capacity", capacity);
        request.getSession().setAttribute("base_price", base_price);
        return "redirect:/confirm_newmotorhome";
    }


    @GetMapping("/save_motorhomeDescription")
    public String saveMotorhomeDescription(@RequestParam("motorhomeDescription_id") int motorhomeDescriptionId, HttpServletRequest request){
        Motorhome motorhome = (Motorhome)request.getSession(true).getAttribute("motorhome");
        motorhomeManager.saveMotorhomeDescription(motorhome, motorhomeDescriptionId);
        return "redirect:/confirm_newmotorhome";
    }

}
