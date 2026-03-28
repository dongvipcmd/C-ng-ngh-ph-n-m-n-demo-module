package com.example.demoCNPM.repository;

import com.example.demoCNPM.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {
    List<Part> findByNameContainingIgnoreCase(String keyword);

}
