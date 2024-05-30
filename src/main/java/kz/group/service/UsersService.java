package kz.group.service;

import kz.group.Security.PersonDetails;
import kz.group.entity.Person;
import kz.group.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.OptionalInt;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

//    public boolean addUser(Person person) {
//        Optional<Person> userFromDb = usersRepository.findByUsername(person.getUsername());
//        if(!userFromDb.isEmpty()){
//            return false;
//        }
//        return true;
//    }

//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Person> usersEntity = usersRepository.findByUsername(username);
//        if (usersEntity.isEmpty()) {
//            throw new UsernameNotFoundException("Пользователь не найден");
//        }
//
//        return new PersonDetails(usersEntity.get());
//    }
}
