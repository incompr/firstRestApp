package firstrestapi1.firstRestApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController // @Controller + @ResponseBody над каждым методом
@RequestMapping("/api")
public class FirstRESTController {

//@ResponseBody можно не писать - т.к. класс - @RestController
@GetMapping("/sayHello")
    public String sayHello(){
        return "Hello World!";
    }


}
