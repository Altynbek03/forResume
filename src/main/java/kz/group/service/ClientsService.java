package kz.group.service;

import kz.group.entity.ClientsEntity;
import kz.group.repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientsService {
    @Autowired
    private ClientsRepository clientsRepository;

    public ClientsEntity findByFirstname(String firstName){
        return clientsRepository.findByFirstName(firstName);
    }
}
