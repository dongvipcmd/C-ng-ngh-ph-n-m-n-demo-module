package com.example.demoCNPM.repository;

import com.example.demoCNPM.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByNameContainingIgnoreCaseOrPhoneContaining(String name, String phone);

}
