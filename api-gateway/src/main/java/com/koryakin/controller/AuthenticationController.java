package com.koryakin.controller;

import com.koryakin.util.JwtTokenUtil;
import com.koryakin.model.AuthToken;
import com.koryakin.model.LoginUser;
import com.koryakin.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;


//TODO: normal security for user-controllers: user see only his info, admin see all users
//TODO: REST API for create, update, add, delete user.  admin can do all he wants/ Users not
//TODO: add bootstrap
//TODO: add views: login, sign-up, info
//TODO: add ability to logout
//TODO: set expiration time for cookies and tokens also set right path for cookies
//TODO: validation for users sign up form and errors
//TODO: remove hibernate
//TODO: start and stop scripts
//TODO: userDetailService ?
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private LoginUserService loginUserService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signupController(){
        return new ModelAndView("signup");
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"}, produces = {"application/json"})
    public ResponseEntity<?> signupUser(@RequestParam("password") String password, @RequestParam("username") String username, HttpServletResponse response ){
        LoginUser newUser = new LoginUser();
        newUser.setUsername(username);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        newUser.setRole("ROLE_USER");
        loginUserService.save(newUser);
        final String token = JwtTokenUtil.generateToken(newUser);
        return ResponseEntity.ok(token);
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView signinController() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"})
    public ResponseEntity<?> attemptAuthentication(@RequestParam("password") String password, @RequestParam("username") String username, HttpServletResponse response){

        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final LoginUser user = loginUserService.findOne(username);
        final String token = JwtTokenUtil.generateToken(user);
        response.setHeader("Set-Cookie", "authCookie="+ token +"; domain=localhost; path=/user/me");
        return ResponseEntity.ok(new AuthToken(token));
    }






    //TODO: REST CRUD- operations
}
