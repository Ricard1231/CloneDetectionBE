package com.example.clonedetection.repositories;

import com.example.clonedetection.models.Snippet;
import com.example.clonedetection.models.SnippetPair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SnippetPairRepository extends JpaRepository<SnippetPair, Integer> {

    Optional<SnippetPair> findBySnippet1AndSnippet2(Snippet snippet1, Snippet snippet2);
}
