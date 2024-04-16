package kz.group.service;

import kz.group.entity.ProductsEntity;
import kz.group.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    public Optional<ProductsEntity> findById(Long id){
        return productsRepository.findById(id);
    }
}
