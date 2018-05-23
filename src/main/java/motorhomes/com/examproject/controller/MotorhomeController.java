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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * @ Alicja Drankowska
 * todo details methods, post update method and comments!
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
        return"motorhomes/fleet";
    }

    @GetMapping("/add_motorhome")
    public String addNewMotorhome(){
        return "motorhomes/create_motorhome/add_motorhome";
    }

    @PostMapping("/add_motorhome")
    public String addNewMotorhome(@RequestParam("licence_plate") String licencePlate, HttpServletRequest request, Model model){
        Motorhome motorhome = motorhomeManager.addNewMotorhome(licencePlate);
        request.getSession(true).setAttribute("motorhome", motorhome);
        List<MotorhomeDescription> motorhomeDescriptions;
        motorhomeDescriptions = motorhomeManager.getExistingMotorhomeDescriptions();
        model.addAttribute("motorhomeDescriptions", motorhomeDescriptions);
        return "motorhomes/create_motorhome/create_description";
    }

    @PostMapping("/create_description")
    public String addNewModel (HttpSession session,
        @RequestParam(value = "brand") String brand, @RequestParam(value = "model") String model,
        @RequestParam(value = "capacity") int capacity, @RequestParam(value = "basePrice") int base_price){

        session.setAttribute("description", motorhomeManager.addNewModel(brand, model, capacity, base_price));
        return "redirect:/confirm_newmotorhome";
    }


    @GetMapping("/save_motorhomeDescription")
    public String saveMotorhomeDescription(@RequestParam("description_id") int motorhomeDescriptionId, HttpSession session){
        session.setAttribute("description", motorhomeManager.getMotorhomeDescription(motorhomeDescriptionId));
        return "redirect:/confirm_newmotorhome";
    }

    @GetMapping("/confirm_newmotorhome")
    public String confirm(HttpSession session) {
        Motorhome motorhome = (Motorhome)session.getAttribute("motorhome");
        motorhomeManager.saveNewMotorhome(motorhome, (MotorhomeDescription) session.getAttribute("description"));
        session.removeAttribute("motorhome");
        session.removeAttribute("description");

        return "redirect:/fleet";
    }

    @GetMapping("/update_motorhome")
    public String updateMotorhome(Model model, @RequestParam("motorhome_id") int motorhomeId){
        Motorhome motorhome = motorhomeManager.getChosenMotorhome(motorhomeId);
        model.addAttribute("motorhome", motorhome);
        model.addAttribute("statusList", motorhomeManager.getMotorhomeStatuses(motorhome));

        return "motorhomes/update_motorhome";
    }

    @PostMapping("update_motorhome")
    public String saveUpdateMotorhome(@RequestParam("motorhome_id") int motorhomeId, @RequestParam("motorhome_status") String motorhomeStatus){
       Motorhome motorhome = motorhomeManager.getChosenMotorhome(motorhomeId);
       motorhome.setMotorhomeStatus(motorhomeStatus);
       motorhomeManager.updateMotorhome(motorhome);

       return "redirect:/fleet";
    }

    @GetMapping("/delete_motorhome")
    public String deleteMotorhome(@RequestParam("motorhome_id") int motorhomeId, Model model) {
        model.addAttribute("motorhome", motorhomeManager.getChosenMotorhome(motorhomeId));
        return "motorhomes/delete_motorhome";
    }

    @PostMapping("/delete_motorhome")
    public String confirmDelete(@RequestParam("motorhome_id") int motorhomeId) {
        motorhomeManager.deleteMotorhome(motorhomeId);
        return "redirect:/fleet";
    }

}
