package com.example.demoCNPM.service.imp;

import com.example.demoCNPM.entity.Client;
import com.example.demoCNPM.repository.ClientRepository;
import com.example.demoCNPM.service.interfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientServiceImp implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client getById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Client not found"));
    }

    @Override
    public List<Client> search(String keyword) {
        return clientRepository.findByNameContainingIgnoreCaseOrPhoneContaining(keyword, keyword);
    }
}
