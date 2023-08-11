package com.example.clonedetection.controllers;

import com.example.clonedetection.dtos.request.AuthenticationReq;
import com.example.clonedetection.dtos.request.RegisterReq;
import com.example.clonedetection.dtos.response.AuthenticationResponse;
import com.example.clonedetection.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController{

    @Autowired
    private final UserService userService;

    @PostMapping("/register")
    @Override
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterReq request) {
        try {
            AuthenticationResponse response = userService.register(request);
            return ResponseEntity.ok(response);
        } catch (InvalidParameterException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthenticationResponse(null));
        }
    }

    @PostMapping("/authenticate")
    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationReq request) {
        try {
            AuthenticationResponse response = userService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthenticationResponse(null));
        }
    }
}
