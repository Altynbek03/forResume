package kz.group.controller;

import kz.group.entity.ClientsEntity;
import kz.group.entity.DocumentsEntity;
import kz.group.service.ClientsService;
import kz.group.service.DocumentGenerator;
import kz.group.service.DocumentsService;
import kz.group.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Optional;

@Controller
@RequestMapping("/documents")
public class DocumentsController {
    @Autowired
    private ClientsService clientsService;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentGenerator documentGenerator;
    @Autowired
    private ProductsService productsService;
    @Autowired
    private DocumentsService documentsService;

    @GetMapping("/generateUserAgreement")
    public String generateUserAgreement(
            @RequestParam int clientId
    ) {
        String finalHTML = null;
        ClientsEntity client = clientsService.findById((long) clientId).get();
        Context context = new Context();
        context.setVariable("client", client);
        finalHTML = springTemplateEngine.process("document/agreement",context);

        documentGenerator.generateDocument(finalHTML,client);


        return "redirect:/clients/clientProfile?id="+clientId;
    }

    @GetMapping("/{documentId}")
    public String getDocument(
            @PathVariable("documentId") long documentId
    ) {
        Optional<DocumentsEntity> clientDocument = documentsService.findById(documentId);
        if(!clientDocument.isPresent()) {
            return null;
        } else {
            return "redirect:/abonements/" + clientDocument.get().getContractFileName();
        }
    }

}
