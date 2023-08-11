package com.example.clonedetection.controllers;

import com.example.clonedetection.dtos.request.SaveResponseReq;
import com.example.clonedetection.dtos.response.GenerateSnippetsResponse;
import com.example.clonedetection.dtos.response.SaveResponseResponse;
import org.springframework.http.ResponseEntity;

public interface SnippetController {

    ResponseEntity<GenerateSnippetsResponse> generateRandom();

    ResponseEntity<SaveResponseResponse> saveResponse(SaveResponseReq request);
}
