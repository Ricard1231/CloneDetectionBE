package com.example.clonedetection.controllers;

import com.example.clonedetection.dtos.request.AuthenticationReq;
import com.example.clonedetection.dtos.request.RegisterReq;
import com.example.clonedetection.dtos.response.AuthenticationResponse;
import org.springframework.http.ResponseEntity;

public interface UserController {

    ResponseEntity<AuthenticationResponse> register(RegisterReq request);

    ResponseEntity<AuthenticationResponse> authenticate(AuthenticationReq request);
}
