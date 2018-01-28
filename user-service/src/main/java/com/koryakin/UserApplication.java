package com.koryakin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @RequestMapping(value = "simple")
    public String simpleMapping(){
        return "simple mapping";
    }

//    @RequestMapping(value = "index")
//    public String simpleMapping(Model model ){
//        model.addAttribute("username", )
//        return "index";
//    }

}
