package kz.group.repository;

import kz.group.entity.DocumentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentsRepository extends JpaRepository<DocumentsEntity,Long> {
    public List<DocumentsEntity> findByClientId(long id);
}
