package kz.group.service;

import kz.group.entity.UsersEntity;
import kz.group.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public UsersEntity findUserByUsername(String username){

        return usersRepository.findByUsername(username);
    }
}
