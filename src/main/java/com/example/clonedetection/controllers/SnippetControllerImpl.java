package com.example.clonedetection.controllers;

import com.example.clonedetection.dtos.request.SaveResponseReq;
import com.example.clonedetection.dtos.response.GenerateSnippetsResponse;
import com.example.clonedetection.dtos.response.SaveResponseResponse;
import com.example.clonedetection.models.Snippet;
import com.example.clonedetection.services.SnippetService;
import com.example.clonedetection.services.UserSnippetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/snippets")
@RequiredArgsConstructor
public class SnippetControllerImpl implements SnippetController {

    @Autowired
    private final SnippetService snippetService;

    @Autowired
    private final UserSnippetService userSnippetService;

    @Override
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<GenerateSnippetsResponse> generateRandom() {
        return ResponseEntity.ok().body(snippetService.generateRandom());
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<SaveResponseResponse> saveResponse(@RequestBody SaveResponseReq request) {
        return ResponseEntity.ok().body(userSnippetService.saveResponse(request));
    }


}
