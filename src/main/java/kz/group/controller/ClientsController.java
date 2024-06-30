package kz.group.controller;

import jakarta.validation.Valid;
import kz.group.DTO.ClientDto;
import kz.group.entity.AbonementEntity;
import kz.group.entity.ClientsEntity;
import kz.group.entity.VisitsEntity;
import kz.group.repository.AbonementRepository;
import kz.group.repository.ClientsRepository;
import kz.group.repository.DocumentsRepository;
import kz.group.repository.ProductsRepository;
import kz.group.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clients")
public class ClientsController {
    @Autowired
    private ClientsService clientsService;
    @Autowired
    private ClientsRepository clientsRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private DocumentsService documentsService;
    @Autowired
    private AbonementService abonementService;
    @Autowired
    private VisitsService visitsService;

    @GetMapping({"/",""})
    public String showClientsPage(Model model) {
        String username = usersService.getUsername();
        boolean isOwner = usersService.isOwner();
        model.addAttribute("username", username);
        model.addAttribute("userRole", isOwner);
        return findPaginated(1,model);
    }

    @PostMapping({"/searchedClients",})
    public String showSortedClientPage(
            Model model,
            @RequestParam String clientLastName
    )
    {
        if(clientLastName.isEmpty()){
            return "redirect:/clients/";
        } else {
            List<ClientsEntity> clients = clientsService.findByLastName(clientLastName);
            model.addAttribute("clients", clients);
        }
        String username = usersService.getUsername();
        boolean isOwner = usersService.isOwner();
        model.addAttribute("username", username);
        model.addAttribute("userRole", isOwner);
        model.addAttribute("clientLastName", clientLastName);
        return "clients/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        String username = usersService.getUsername();
        boolean isOwner = usersService.isOwner();
        model.addAttribute("username", username);
        model.addAttribute("userRole", isOwner);
        ClientDto clientDto = new ClientDto();
        model.addAttribute("clientDto", clientDto);
        return "clients/addClient";
    }

    @PostMapping("/create")
    public String addClient(
            @Valid @ModelAttribute ClientDto clientDto,
            BindingResult result,
            Model model
    ) {
        String username = usersService.getUsername();
        boolean isOwner = usersService.isOwner();
        model.addAttribute("username", username);
        model.addAttribute("userRole", isOwner);
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
        client.setDateOfBorn(clientDto.getDateOfBorn());
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
        String username = usersService.getUsername();
        boolean isOwner = usersService.isOwner();
        model.addAttribute("username", username);
        model.addAttribute("userRole", isOwner);
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
            clientDto.setDateOfBorn(client.getDateOfBorn());

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
        String username = usersService.getUsername();
        boolean isOwner = usersService.isOwner();
        model.addAttribute("username", username);
        model.addAttribute("userRole", isOwner);
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
            client.setDateOfBorn(clientDto.getDateOfBorn());

            clientsRepository.save(client);
        } catch (Exception exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
        return "redirect:/clients";
    }

    @GetMapping("/delete")
    public String deleteClient(
            Model model,
            @RequestParam int id
    ){
        try {
            ClientsEntity client = clientsRepository.findById(id).get();

            Path imagePath = Paths.get("public/images/" + client.getImageFileName());

            try {
                Files.delete(imagePath);
            } catch (Exception exception){
                System.out.println("Exception: " + exception.getMessage());
            }

            clientsRepository.delete(client);
        } catch (Exception exception) {
            System.out.println("Exception: " + exception.getMessage());
        }
        return "redirect:/clients";
    }

    @GetMapping("/clientProfile")
    public String clientProfile(
            @RequestParam long id,
            Model model
    ){
        boolean hasAgreement = documentsService.hasAgreement(id);
        String agreementFileName = documentsService.clientAgreementFileName(id);
        String username = usersService.getUsername();
        boolean isOwner = usersService.isOwner();

        //Пробный отрезок для проверки входа и выхода в зал
        LocalDateTime lastVisit = visitsService.lastVisit(id);
        model.addAttribute("lastVisit", lastVisit);
        //Конец пробного отрезка

        List<AbonementEntity> abonementList = abonementService.abonementList(id);
        model.addAttribute("abonementList", abonementList);

        model.addAttribute("username", username);
        model.addAttribute("userRole", isOwner);
        model.addAttribute("clientContractExists", hasAgreement);
        ClientsEntity client = clientsRepository.findById(id).get();
        model.addAttribute("agreementFileName", agreementFileName);
        model.addAttribute("client", client);
        return "clients/clientProfile";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(
            @PathVariable (value = "pageNo") int pageNo,
            Model model
    ){
        int pageSize = 15;
        String username = usersService.getUsername();
        boolean isOwner = usersService.isOwner();
        model.addAttribute("username", username);
        model.addAttribute("userRole", isOwner);

        Page<ClientsEntity> page = clientsService.findPaginated(pageNo, pageSize);
        List<ClientsEntity> clients = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("clients", clients);
        return "clients/index";
    }
}
