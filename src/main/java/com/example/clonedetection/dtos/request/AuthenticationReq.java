package com.example.clonedetection.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationReq {

    @Email(message = "Email format invalid")
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
