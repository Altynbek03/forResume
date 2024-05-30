package kz.group.service;

import kz.group.entity.Person;
import kz.group.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegistrationService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean addUser(Person person) {
        Optional<Person> personFromDb = usersRepository.findByUsername(person.getUsername());
        if(personFromDb.isEmpty()){
            return false;
        }
        return true;
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        person.setActive(true);
        person.setCreatedAt(LocalDateTime.now());
        usersRepository.save(person);
    }

}
