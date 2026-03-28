package com.example.demoCNPM.service.interfaces;

import com.example.demoCNPM.entity.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAll();

    Client save(Client client);

    Client getById(Long id);


    List<Client> search(String keyword);
}
