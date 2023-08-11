package com.example.clonedetection.services;

import com.example.clonedetection.dtos.request.SaveResponseReq;
import com.example.clonedetection.dtos.response.SaveResponseResponse;

public interface UserSnippetService {
    SaveResponseResponse saveResponse(SaveResponseReq request);
}
