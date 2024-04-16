package kz.group.controller;

import kz.group.entity.PaymentsEntity;
import kz.group.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public String getPayment(@RequestParam("id") Long id){
        Optional<PaymentsEntity> optional = paymentService.findById(id);
        if(optional.isPresent()){
            return optional.get().toString();
        }
        return "Такая оплата не производилась";
    }

}
