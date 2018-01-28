package com.koryakin.controllers;

//import com.koryakin.util.CookieUtil;
//import com.koryakin.util.JwtTokenUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {


    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public String register(HttpServletRequest request, Model model)  {

//        String authToken = CookieUtil.getAuthToken(request);
//        String name = new JwtTokenUtil().getUsernameFromToken(authToken);
        model.addAttribute("name", "me");

      return "homepage";
    }
}
