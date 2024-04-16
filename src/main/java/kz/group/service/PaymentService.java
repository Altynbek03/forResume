package kz.group.service;

import kz.group.entity.PaymentsEntity;
import kz.group.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentsRepository paymentsRepository;

    public Optional<PaymentsEntity> findById(Long id){
        return paymentsRepository.findById(id);
    }
}
