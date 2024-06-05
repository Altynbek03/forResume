package kz.group.service;

import kz.group.entity.ClientsEntity;
import kz.group.repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {
    @Autowired
    private ClientsRepository clientsRepository;

    public Optional<ClientsEntity> findById(Long id){
        return clientsRepository.findById(id);
    }

    public List<ClientsEntity> findByFistName(String fistName){
        return clientsRepository.findByFirstName(fistName);
    }

    public List<ClientsEntity> findByLastName(String lastName){
        lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();
        return clientsRepository.findByLastName(lastName);
    }

}
