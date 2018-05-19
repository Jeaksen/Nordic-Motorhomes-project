package motorhomes.com.examproject.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {

//    @GetMapping("/")
//    public String index(){
//        return "home";
//    }

//    @GetMapping("/guest")
//    public String guest(){
//        return"guest";
//    }


//    @GetMapping("/login")
//    public String login(){
//        return"login";
//    }

    @GetMapping("/fleet")
    public String fleet(){
        return"fleet";
    }

    @GetMapping("/reservations")
    public String reservations(){
        return"reservations";
    }

    @GetMapping("/repairs")
    public String repairs(){
        return"repairs";
    }

    @GetMapping("/customers")
    public String customers(){
        return "customers";
    }

}
