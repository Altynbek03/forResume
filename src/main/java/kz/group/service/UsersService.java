package kz.group.service;

import kz.group.Security.PersonDetails;
import kz.group.entity.Person;
import kz.group.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.OptionalInt;

@Service
public class UsersService {

    public String getUsername() {
        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) loggedUser.getPrincipal();
        return personDetails.getUsername();
    }

    public boolean isOwner() {
        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) loggedUser.getPrincipal();
        return personDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_OWNER"));
    }

}
