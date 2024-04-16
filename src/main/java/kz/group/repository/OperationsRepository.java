package kz.group.repository;

import kz.group.entity.OperationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationsRepository  extends JpaRepository<OperationsEntity,Long> {
}
