package com.koryakin.controller;

import com.koryakin.model.AuthToken;
import com.koryakin.model.LoginUser;
import com.koryakin.service.LoginUserService;
import com.koryakin.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//TODO: all api operations

@RequestMapping("/api")
public class ApiController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final LoginUserService loginUserService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public ApiController(BCryptPasswordEncoder bCryptPasswordEncoder, LoginUserService loginUserService, AuthenticationManager authenticationManager) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.loginUserService = loginUserService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST , consumes = {"application/json"})
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        final LoginUser user = loginUserService.findOne(loginUser.getUsername());
        final String token = JwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new AuthToken(token));
    }

//    @RequestMapping(value = "/create", method = RequestMethod.POST)
//    public ResponseEntity<?> createUser(@RequestBody LoginUser loginUser, @RequestParam("role") String  role) {
//        LoginUser newUser = new LoginUser();
//        newUser.setUsername(loginUser.getUsername());
//        newUser.setPassword(bCryptPasswordEncoder.encode(loginUser.getPassword()));
//        newUser.setRole("ROLE_USER");
//        loginUserService.save(newUser);
//        final String token = JwtTokenUtil.generateToken(newUser);
//        return ResponseEntity.ok(token);
//    }
//
//    @RequestMapping(value ="/get", method = RequestMethod.GET)
//    public ResponseEntity<LoginUser> getUser()


}
