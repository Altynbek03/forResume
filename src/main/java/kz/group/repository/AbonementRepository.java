package kz.group.repository;

import kz.group.entity.AbonementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbonementRepository extends JpaRepository<AbonementEntity, Long> {
    List<AbonementEntity> findByClientId(int id);
}
