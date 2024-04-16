package kz.group.controller;

import kz.group.entity.SubscriptionEntity;
import kz.group.repository.SubscriptionRepository;
import kz.group.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping
    public String getSubscription(@RequestParam("id") Long id) {
        Optional<SubscriptionEntity> optional = subscriptionService.findById(id);
        if(optional.isPresent()){
            return optional.get().toString();
        }
        return "Такого абономента не существует";
    }
}
