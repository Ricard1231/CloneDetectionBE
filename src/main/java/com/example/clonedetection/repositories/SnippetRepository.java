package com.example.clonedetection.repositories;

import com.example.clonedetection.models.File;
import com.example.clonedetection.models.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SnippetRepository extends JpaRepository<Snippet, Integer> {

    Optional<Snippet> findByCode(String code);

    List<Snippet> findAllByFile(File file1);
}
