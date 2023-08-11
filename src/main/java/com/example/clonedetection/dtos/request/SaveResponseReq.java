package com.example.clonedetection.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveResponseReq {

    @NotBlank
    private Integer snippetPairId;
    @NotBlank
    private Integer response;
}
