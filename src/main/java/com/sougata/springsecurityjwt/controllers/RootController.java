package com.sougata.springsecurityjwt.controllers;

import com.sougata.springsecurityjwt.models.AuthenticationRequest;
import com.sougata.springsecurityjwt.models.AuthenticationResponse;
import com.sougata.springsecurityjwt.services.MyUserDetailsService;
import com.sougata.springsecurityjwt.utils.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final JWTUtil jwtUtil;

    public RootController(AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService,
                          JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @RequestMapping(value = "/hello")
    public String hello() {
        return "<h1>Hello World!</h1>";
    }

    @RequestMapping(value = "/hello_again")
    public String helloAgain() {
        return "<h1>Hello Again!</h1>";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUserName(), authenticationRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect userName and password!");
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUserName());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}




















