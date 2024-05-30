package kz.group.repository;

import kz.group.entity.ClientsEntity;
import kz.group.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientsRepository extends JpaRepository<ClientsEntity, Long> {

    Optional<ClientsEntity> findByFirstName(String firstName);
    Optional<ClientsEntity> findById(int id);

}
