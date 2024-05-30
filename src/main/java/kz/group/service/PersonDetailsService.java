package kz.group.service;

import kz.group.Security.PersonDetails;
import kz.group.entity.Person;
import kz.group.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    public PersonDetailsService(kz.group.repository.UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = usersRepository.findByUsername(username);
        if(person.isEmpty()){
            throw new UsernameNotFoundException("пользователь не найден");
        }
        return new PersonDetails(person.get());
    }
}
