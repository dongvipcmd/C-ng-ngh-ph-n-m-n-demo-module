package com.example.demoCNPM.repository;

import com.example.demoCNPM.entity.ServiceClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceClass, Long> {
    List<ServiceClass> findByNameContainingIgnoreCase(String keyword);

}
