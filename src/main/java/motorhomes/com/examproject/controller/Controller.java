package motorhomes.com.examproject.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {


    @GetMapping("/customers")
    public String customers(){
        return "customers";
    }

}
