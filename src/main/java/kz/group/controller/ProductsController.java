package kz.group.controller;

import kz.group.entity.ProductsEntity;
import kz.group.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    @GetMapping
    String getProduct(@RequestParam("id") Long id) {
        Optional<ProductsEntity> optional = productsService.findById(id);
        if(optional.isPresent()){
            return optional.get().toString();
        }
        return "Такого продукта не существует";
    }
}
