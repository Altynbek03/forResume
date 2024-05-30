package kz.group.repository;

import kz.group.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByUsername(String username);
}
