package kz.group.repository;

import kz.group.entity.AbonementEntity;
import kz.group.entity.ClientsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbonementRepository extends JpaRepository<AbonementEntity, Long> {
    List<AbonementEntity> findByClientId(long id);
}
