package kz.group.controller;

import jakarta.validation.Valid;
import kz.group.entity.Person;
import kz.group.repository.UsersRepository;
import kz.group.service.RegistrationService;
import kz.group.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsersService usersService;

    private final RegistrationService registrationService;
    private final UsersRepository usersRepository;

    @Autowired
    public AuthController(RegistrationService registrationService, UsersRepository usersRepository) {
        this.registrationService = registrationService;
        this.usersRepository = usersRepository;
    }


    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person,
                                   Model model
    ) {
        String username = usersService.getUsername();
        boolean isOwner = usersService.isOwner();
        model.addAttribute("username", username);
        model.addAttribute("userRole", isOwner);
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person")
                                      @Valid Person person,
                                      Map<String,Object> existUser,
                                      Model model
    ) {
        String username = usersService.getUsername();
        boolean isOwner = usersService.isOwner();
        model.addAttribute("username", username);
        model.addAttribute("userRole", isOwner);
        if(registrationService.addUser(person)){
            existUser.put("message","Пользователь зарегистрирован");
            return "/auth/registration";
        } else {
            registrationService.register(person);
            return "redirect:/users";
        }
    }
}
