package kz.group.repository;

import kz.group.entity.VisitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitsRepository extends JpaRepository<VisitsEntity, Long> {
    List<VisitsEntity> findByClientId(long clientId);
    List<VisitsEntity> findByVisitDateIsNotNullAndDepartureDateIsNull();
}
