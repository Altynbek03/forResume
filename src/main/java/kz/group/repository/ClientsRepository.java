package kz.group.repository;

import kz.group.entity.ClientsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientsRepository extends JpaRepository<ClientsEntity, Long> {

    List<ClientsEntity> findByFirstName(String name);

    Optional<ClientsEntity> findById(long id);

    List<ClientsEntity> findByLastName(String lastName);


}
