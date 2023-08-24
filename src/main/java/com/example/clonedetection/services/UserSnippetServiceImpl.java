package com.example.clonedetection.services;

import com.example.clonedetection.dtos.request.SaveResponseReq;
import com.example.clonedetection.dtos.response.SaveResponseResponse;
import com.example.clonedetection.models.SnippetPair;
import com.example.clonedetection.models.User;
import com.example.clonedetection.models.UserSnippet;
import com.example.clonedetection.repositories.SnippetPairRepository;
import com.example.clonedetection.repositories.UserSnippetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserSnippetServiceImpl implements UserSnippetService{

    private final UserSnippetRepository userSnippetRepository;
    private final SnippetPairRepository snippetPairRepository;

    @Override
    public SaveResponseResponse saveResponse(SaveResponseReq request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SnippetPair pair = snippetPairRepository.getReferenceById(request.getSnippetPairId());
        userSnippetRepository.save(
                UserSnippet.builder()
                        .user(user)
                        .snippetPair(pair)
                        .type(request.getResponse())
                        .dateCreated(new Date())
                .build()
        );
        return SaveResponseResponse.builder()
                .isSuccess(true)
                .build();
    }
}
