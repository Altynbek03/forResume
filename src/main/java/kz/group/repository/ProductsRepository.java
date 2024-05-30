package kz.group.repository;

import kz.group.entity.ProductsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository <ProductsEntity,Long> {
    Optional<ProductsEntity> findByProductName(String productName);
    Optional<ProductsEntity> findById(int id);
}
