package com.example.clonedetection.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReq {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private Double experience;
    @NotBlank
    private String education;
}
