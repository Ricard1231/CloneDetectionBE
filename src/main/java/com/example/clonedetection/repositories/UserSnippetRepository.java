package com.example.clonedetection.repositories;

import com.example.clonedetection.models.UserSnippet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSnippetRepository extends JpaRepository<UserSnippet, Integer> {
}
