package kz.group.controller;

import kz.group.entity.OperationsEntity;
import kz.group.service.OperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/operations")
public class OperationsController {
    @Autowired
    private OperationsService operationsService;

    @GetMapping
    String getOperation(@RequestParam("id") Long id) {
        Optional<OperationsEntity> optional = operationsService.findById(id);
        if(optional.isPresent()){
            return optional.get().toString();
        }
        return "Такая операция не происходила";
    }

}
