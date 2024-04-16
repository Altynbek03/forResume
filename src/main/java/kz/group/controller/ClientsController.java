package kz.group.controller;

import kz.group.entity.ClientsEntity;
import kz.group.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientsController {
    @Autowired
    private ClientsService clientsService;

    @GetMapping
    public String getClient(@RequestParam("firstname") String firstName) {
        ClientsEntity clientsEntity = clientsService.findByFirstname(firstName);
            if(clientsEntity == null) {
                return "Такого клиента не существует";
            }
            return clientsEntity.toString();
    }
}
