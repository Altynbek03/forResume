package kz.group.controller;

import kz.group.entity.UsersEntity;
import kz.group.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping
    public String getUser(@RequestParam("username") String username) {
        UsersEntity usersEntity = usersService.findUserByUsername(username);
        if (usersEntity == null) {
            return "Пользователь не найден";
        }
        return usersEntity.toString();
    }
}
