package kz.group.controller;

import kz.group.DTO.ClientInGym;
import kz.group.entity.AbonementEntity;
import kz.group.repository.AbonementRepository;
import kz.group.service.AbonementService;
import kz.group.service.UsersService;
import kz.group.service.VisitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("visits/v1")
public class VisitsController {
    @Autowired
    private VisitsService visitsService;
    @Autowired
    private AbonementService abonementService;
    @Autowired
    private AbonementRepository abonementRepository;
    @Autowired
    private UsersService usersService;

    @GetMapping("/enterTheGym")
    public String enterTheGym(
            RedirectAttributes redirectAttributes,
            @RequestParam long clientId,
            @RequestParam long abonementId
    ){
        visitsService.enterTheGym(clientId,abonementId);
        //TODO
        AbonementEntity abonement = abonementRepository.findById(abonementId).get();
        if(abonement.getStartDate()==null && abonement.getEndDate()==null){
            abonementService.abonementStarted(abonementId);
        }
        redirectAttributes.addFlashAttribute("clientInGym",false);
        return "redirect:/clients/clientProfile?id=" + clientId;
    }

    @GetMapping("/exitTheGym")
    public String exitTheGym(
            @RequestParam long clientId,
            @RequestParam long abonementId
    ){
        visitsService.exitTheGym(clientId,abonementId);
        return "redirect:/clients/clientProfile?id=" + clientId;
    }

    @GetMapping("/inGym")
    public String inGym(
            Model model
    ) {
        String username = usersService.getUsername();
        boolean isOwner = usersService.isOwner();
        model.addAttribute("username", username);
        model.addAttribute("userRole", isOwner);

        List<ClientInGym> clientsInGym = visitsService.getClientsInGym();
        if(clientsInGym.size() > 0){
            model.addAttribute("clientsInGyms", clientsInGym);
        }
        return "clients/inGym";
    }
}
