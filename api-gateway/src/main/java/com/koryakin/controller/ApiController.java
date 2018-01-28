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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/api")
public class ApiController {

    private final LoginUserService loginUserService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public ApiController(LoginUserService loginUserService, AuthenticationManager authenticationManager) {
        this.loginUserService = loginUserService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final LoginUser user = loginUserService.findOne(loginUser.getUsername());
        final String token = JwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new AuthToken(token));
    }
}
