package kz.group.repository;

import kz.group.entity.ClientsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<ClientsEntity, Long> {

    public ClientsEntity findByFirstName(String firstName);

}
