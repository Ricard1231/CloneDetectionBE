package com.example.clonedetection.services;

import com.example.clonedetection.config.JwtService;
import com.example.clonedetection.dtos.request.AuthenticationReq;
import com.example.clonedetection.dtos.request.RegisterReq;
import com.example.clonedetection.dtos.response.AuthenticationResponse;
import com.example.clonedetection.models.User;
import com.example.clonedetection.models.enumeration.Role;
import com.example.clonedetection.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Override
    public User saveUser(RegisterReq request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        return userRepository.save(user);
    }

    @Override
    public AuthenticationResponse register(RegisterReq request) {
        Optional<User> isUserExists = userRepository.findByEmail(request.getEmail());
        if (isUserExists.isPresent()) {
            throw new InvalidParameterException();
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .experience(request.getExperience())
                .education(request.getEducation())
                .isEngineering(request.getIsEngineering())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationReq request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
