package kz.group.service;

import kz.group.entity.OperationsEntity;
import kz.group.repository.OperationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OperationsService {
    @Autowired
    private OperationsRepository operationsRepository;

    public Optional<OperationsEntity> findById(Long id){
        return operationsRepository.findById(id);
    }
}
