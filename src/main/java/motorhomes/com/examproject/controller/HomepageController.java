package motorhomes.com.examproject.controller;

import motorhomes.com.examproject.applicationLogic.HomepageManager;
import motorhomes.com.examproject.model.User;
import motorhomes.com.examproject.repositories.UsersDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;


/**
 * This class is responsible for responding to request send to the server
 * It manages homepage and login/register pages
 * @ Pawel Pohl
 */
@Controller
public class HomepageController {

    private HomepageManager homepageManager;


    @Autowired
    public HomepageController(HomepageManager homepageManager) {
        this.homepageManager = homepageManager;
    }

//    public HomepageController(){
//    this.homepageManager = new HomepageManager(new UsersDBRepository());
//    }


    /**
     * This method responds to a login request from user
     * It checks if entered user data matches any saved users data
     * If it does page with fleet is being opened
     * If entered user's data doesn't match saved data or there was any other error
     * error message (name="error_message") is added to the model object and homepage is being opened
     * @param user User object with username and password entered by a user
     * @param request HttpServletRequest used to save session data
     * @param model Model object used to save attributes for later Thymeleaf use
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest request, Model model){
        byte respond = homepageManager.login(user);
        switch (respond){
            case 1:
                request.getSession().setAttribute("user", user);
                return "redirect:/fleet";
            case 0:
                model.addAttribute("error_message", "An error occurred while getting the data, please retry");
                break;
            case -1:
                model.addAttribute("error_message", "Incorrect password, please retry");
                break;
            case -2:
                model.addAttribute("error_message", "User with such username was not found, please retry");
                break;
            default:
                model.addAttribute("error_message", "An unknown error has occurred, please retry");
                break;
        }
        System.out.println("error!");
            return "login";
    }

    /**
     * This method responds to a register request from user
     * It checks if entered user data is correct (not empty)
     * If it does page with fleet is being opened
     * If entered user's data doesn't match saved data or there was any other error
     * error message attribute (name="error_message") is added to the model object and login is being opened
     * @param user User object with username and password entered by a user
     * @param request HttpServletRequest used to save session data
     * @param model Model object used to save attributes for later Thymeleaf use
     */
    @PostMapping("/register")
    public String register(@ModelAttribute User user, HttpServletRequest request, Model model){
        byte respond = homepageManager.register(user);
        switch (respond){
            case 1:
                request.getSession().setAttribute("user", user);
                return "redirect:/fleet";
            case 0:
                model.addAttribute("error_message", "An error occurred while getting the data, please retry");
                break;
            case -1:
                model.addAttribute("error_message", "Please enter a password");
                break;
            case -2:
                model.addAttribute("error_message", "Please enter a valid username");
                break;
            default:
                model.addAttribute("error_message", "An unknown error has occurred, please retry");
                break;
        }
        return "login";
    }

    /**
     * This method responds to homepage request and opens the homepage
     */
    @GetMapping("/")
    public String index(){
        return "home";
    }

    /**
     * This method respond to guest request and opens corresponding page
     */
    @GetMapping("/guest")
    public String guest(){
        return"guest";
    }

    /**
     * This methods responds to login request and opens page with login form
     */
    @GetMapping("/login")
    public String login(){
        return"login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}
