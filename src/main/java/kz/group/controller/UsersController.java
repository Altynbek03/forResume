package kz.group.controller;

import jakarta.validation.Valid;
import kz.group.DTO.UserDto;
import kz.group.entity.Person;
import kz.group.repository.UsersRepository;
import kz.group.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping({"","/"})
    public String getUser(
            Model model
    ) {
        String username = usersService.getUsername();
        boolean isOwner = usersService.isOwner();
        model.addAttribute("username", username);
        model.addAttribute("userRole", isOwner);
        List<Person> users = usersRepository.findAll();
        model.addAttribute("users", users);
        return "users/index";
    }



    @GetMapping("/edit")
    public String editUser(
        Model model,
        @RequestParam int id
    ){
        String username = usersService.getUsername();
        boolean isOwner = usersService.isOwner();
        model.addAttribute("username", username);
        model.addAttribute("userRole", isOwner);
        try {
            Person person = usersRepository.findById((long) id).get();
            model.addAttribute("person", person);
            UserDto userDto = new UserDto();

            userDto.setUsername(person.getUsername());

            model.addAttribute("userDto", userDto);
        } catch (Exception exception) {
            System.out.println("exception: " + exception.getMessage());
            return "users/editUser";
        }
        return "users/editUser";
    }

    @PostMapping("/edit")
    public String updateUser(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute UserDto userDto,
            BindingResult result
    ){
        String username = usersService.getUsername();
        boolean isOwner = usersService.isOwner();
        model.addAttribute("username", username);
        model.addAttribute("userRole", isOwner);
        try {
            Person person = usersRepository.findById((long)id).get();
            model.addAttribute("person", person);
            if(result.hasErrors()) {
                return "users/editUser";
            }

            person.setUsername(userDto.getUsername());
            usersRepository.save(person);
        } catch (Exception exception) {
            System.out.println("exception: " + exception.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String deleteUser(
            @RequestParam int id
    ){
        Person person = usersRepository.findById((long)id).get();
        usersRepository.delete(person);
        return "redirect:/users";
    }
}
