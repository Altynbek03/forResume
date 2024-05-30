package kz.group.controller;

import jakarta.validation.Valid;
import kz.group.DTO.ClientDto;
import kz.group.entity.ClientsEntity;
import kz.group.repository.ClientsRepository;
import kz.group.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientsController {
    @Autowired
    private ClientsService clientsService;

    @Autowired
    private ClientsRepository clientsRepository;

//    @GetMapping
//    public String getClient(@RequestParam("firstname") String firstName) {
//        ClientsEntity clientsEntity = clientsService.findByFirstname(firstName);
//            if(clientsEntity == null) {
//                return "Такого клиента не существует";
//            }
//            return clientsEntity.toString();
//    }

    @GetMapping({"/",""})
    public String showClientsPage(Model model) {
        List<ClientsEntity> clients = clientsRepository.findAll();
        model.addAttribute("clients", clients);
        return "clients/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        ClientDto clientDto = new ClientDto();
        model.addAttribute("clientDto", clientDto);
        return "clients/addClient";
    }

    @PostMapping("/create")
    public String addClient(
            @Valid @ModelAttribute ClientDto clientDto,
            BindingResult result
    ) {
        if (clientDto.getImageFile().isEmpty()){
            result.addError(new FieldError("clientDto","imageFile","Не корректный файл или файл не выбран"));
        }

        if(result.hasErrors()){
            return "clients/addClient";
        }

        MultipartFile image = clientDto.getImageFile();
        Date created = new Date();
        String strogeFileName = created.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if(!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try(InputStream inputStream = image.getInputStream()){
                Files.copy(inputStream, Paths.get(uploadDir + strogeFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch(IOException exception){
            System.out.println("Ошибка: " + exception.getMessage());
        }

        ClientsEntity client = new ClientsEntity();
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setPatronymic(clientDto.getPatronymic());
        client.setContactNumber(clientDto.getContactNumber());
        client.setEmail(clientDto.getEmail());
        client.setGender(clientDto.getGender());
        client.setAgreement(true);
        client.setCreateDate(LocalDateTime.now());
        client.setImageFileName(strogeFileName);

        clientsRepository.save(client);

        return "redirect:/clients";
    }

    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
    ){
        try {
            ClientsEntity client = clientsRepository.findById(id).get();
            model.addAttribute("client", client);

            ClientDto clientDto = new ClientDto();
            clientDto.setFirstName(client.getFirstName());
            clientDto.setLastName(client.getLastName());
            clientDto.setPatronymic(client.getPatronymic());
            clientDto.setContactNumber(client.getContactNumber());
            clientDto.setEmail(client.getEmail());
            clientDto.setGender(client.getGender());

            model.addAttribute("clientDto", clientDto);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/clients";
        }
        return "clients/editClient";
    }

    @PostMapping("/edit")
    public String updateClient(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute ClientDto clientDto,
            BindingResult result
    ) {
        try {
            ClientsEntity client = clientsRepository.findById(id).get();
            model.addAttribute("client", client);

            if(result.hasErrors()){
                return "clients/editClient";
            }

            if (!clientDto.getImageFile().isEmpty()){
                //Удаление старой фотографии
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir + client.getImageFileName());

                try {
                    Files.delete(oldImagePath);
                }
                catch (Exception exception){
                    System.out.println("Ошибка: " + exception.getMessage());
                }

                //сохранение нового файла
                MultipartFile image = clientDto.getImageFile();
                Date created = new Date();
                String storageFileName = created.getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }

                client.setImageFileName(storageFileName);
            }

            client.setFirstName(clientDto.getFirstName());
            client.setLastName(clientDto.getLastName());
            client.setPatronymic(clientDto.getPatronymic());
            client.setContactNumber(clientDto.getContactNumber());
            client.setEmail(clientDto.getEmail());
            client.setGender(clientDto.getGender());

            clientsRepository.save(client);
        } catch (Exception exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
        return "redirect:/clients";
    }

}
