package com.example.clonedetection.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateSnippetsResponse {

    private Integer pairId;
    private String snippet1;
    private String snippet2;
}
