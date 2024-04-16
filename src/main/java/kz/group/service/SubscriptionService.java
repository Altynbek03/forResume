package kz.group.service;

import kz.group.entity.SubscriptionEntity;
import kz.group.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Optional<SubscriptionEntity> findById(Long id) {
        return subscriptionRepository.findById(id);
    }
}
