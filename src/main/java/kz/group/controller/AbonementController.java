package kz.group.controller;

import kz.group.entity.ClientsEntity;
import kz.group.entity.ProductsEntity;
import kz.group.repository.ClientsRepository;
import kz.group.repository.ProductsRepository;
import kz.group.service.AbonementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


import java.util.List;

@Controller
@RequestMapping("/abonement/")
public class AbonementController {
    @Autowired
    private final ClientsRepository clientsRepository;
    @Autowired
    private final ProductsRepository productsRepository;
    @Autowired
    private final AbonementService abonementService;
    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    public AbonementController(ClientsRepository clientsRepository, ProductsRepository productsRepository, AbonementService abonementService) {
        this.clientsRepository = clientsRepository;
        this.productsRepository = productsRepository;
        this.abonementService = abonementService;
    }

    @GetMapping("/setAbonement")
    public String getAbonement(
        Model model,
        @RequestParam int clientId
    ) {
        ClientsEntity client = clientsRepository.findById(clientId).get();
        model.addAttribute("client", client);
        List<ProductsEntity> products = productsRepository.findAll();
        model.addAttribute("products", products);
        return "abonement/setAbonement";
    }

    @PostMapping("/setAbonement")
    public String setAbonement(
        @RequestParam int clientId,
        @RequestParam int productId,
        RedirectAttributes redirectAttributes
    ){
        boolean clientHasAbonement = abonementService.hasAbonement(clientId, productId);
        if(!clientHasAbonement) {
            String finalHTML = null;
            ClientsEntity client = clientsRepository.findById(clientId).get();
            ProductsEntity product = productsRepository.findById(productId).get();
            Context context = new Context();
            context.setVariable("client", client);
            context.setVariable("product", product);
            finalHTML = springTemplateEngine.process("document/abonement", context);
            abonementService.appointAbonement(clientId, productId,finalHTML);
            return "redirect:/clients/clientProfile?id="+clientId;
        }
        redirectAttributes.addFlashAttribute("message","У клиента уже имеется такой активный абонемент");
        return "redirect:/abonement/setAbonement?clientId="+clientId;
    }
    //Перенести функционал с клиент контроллера по назначению абонемента TODO
}
