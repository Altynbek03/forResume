package kz.group.repository;

import jakarta.persistence.Entity;
import kz.group.entity.PaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<PaymentsEntity, Long> {
}
