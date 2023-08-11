package com.example.clonedetection.repositories;

import com.example.clonedetection.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Integer> {

    Optional<File> findByPath(String path);
}
