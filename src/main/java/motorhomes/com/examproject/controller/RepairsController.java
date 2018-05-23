package motorhomes.com.examproject.controller;

import motorhomes.com.examproject.applicationLogic.RepairsManager;
import motorhomes.com.examproject.applicationLogic.ReservationsManager;
import motorhomes.com.examproject.model.Repair;
import motorhomes.com.examproject.repositories.MotorhomeDbRepository;
import motorhomes.com.examproject.repositories.RepairsDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;

@Controller
public class RepairsController {

    private RepairsManager repairsManager;

    @Autowired
    public RepairsController (RepairsManager repairsManager){
        this.repairsManager = repairsManager;
    }

    @GetMapping("/repairs")
    public String getRepairs(Model model){
        List<Repair> repairs= repairsManager.getRepairs();
        model.addAttribute("repairs", repairs);
        return"repairs/repairs";
    }

    @GetMapping ("/new_repair")
    public String newRepair(){
        return "repairs/newrepair";
    }

    @PostMapping("/new_repair")
    public String newRepair(@ModelAttribute Repair repair){
         repairsManager.createRepair(repair);
         return "redirect:/repairs";
    }

    @GetMapping("/update_repair")
    public String updateRepair(@RequestParam ("repairId") int repairId, Model model){
        Repair repair= repairsManager.getRepair(repairId);
        model.addAttribute("repair",repair);
        return"repairs/updaterepair";
    }

    @PostMapping("update_repair")
    public String updateRepair(@ModelAttribute Repair repair){
        repairsManager.updateRepair(repair);
        return "redirect:/repairs";

    }

    @GetMapping ("/repair_details")
    public String repairDetails(@RequestParam("repairId") int repairId, Model model){
        Repair repair=repairsManager.getRepair(repairId);
        model.addAttribute("repair", repair);
        return"repairs/repairdetails";

    }

    @GetMapping("/delete_repair")
    public String deleteRepair(@RequestParam("repairId") int repairId, Model model){
        Repair repair= repairsManager.getRepair(repairId);
        model.addAttribute("repair", repair);
        return"repairs/deleterepair";
    }

    @PostMapping("/delete_repair")
    public String deleteRepair(@ModelAttribute Repair repair){
        repairsManager.deleteRepair(repair.getRepairId());
        return"redirect:/repairs";
    }








}
