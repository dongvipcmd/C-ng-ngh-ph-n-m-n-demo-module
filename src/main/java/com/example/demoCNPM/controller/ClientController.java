package com.example.demoCNPM.controller;

import com.example.demoCNPM.entity.Client;
import com.example.demoCNPM.service.interfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public String listClient(Model model){
        model.addAttribute("clients", clientService.getAll());
        return "client/client-list";
    }

    @GetMapping("/create")
    public String createForm(Model model){
        model.addAttribute("client", new Client());
        return "client/client-form";
    }

    @PostMapping
    public String create(@ModelAttribute Client client){
        clientService.save(client);
        return "redirect:/reception/search-client?keyword=" + client.getName();
    }

}
