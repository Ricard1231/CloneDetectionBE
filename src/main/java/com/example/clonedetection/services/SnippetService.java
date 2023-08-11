package com.example.clonedetection.services;

import com.example.clonedetection.dtos.response.GenerateSnippetsResponse;
import com.example.clonedetection.models.Snippet;

public interface SnippetService {

    GenerateSnippetsResponse generateRandom();
}
