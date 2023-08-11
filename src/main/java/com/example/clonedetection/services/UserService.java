package com.example.clonedetection.services;

import com.example.clonedetection.dtos.request.AuthenticationReq;
import com.example.clonedetection.dtos.request.RegisterReq;
import com.example.clonedetection.dtos.response.AuthenticationResponse;
import com.example.clonedetection.models.User;

public interface UserService {

    User saveUser(RegisterReq request);

    AuthenticationResponse register(RegisterReq request);

    AuthenticationResponse authenticate(AuthenticationReq request);
}
