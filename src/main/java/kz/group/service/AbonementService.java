package kz.group.service;

import jakarta.validation.constraints.NotNull;
import kz.group.entity.AbonementEntity;
import kz.group.entity.DocumentsEntity;
import kz.group.entity.ProductsEntity;
import kz.group.repository.AbonementRepository;
import kz.group.repository.DocumentsRepository;
import kz.group.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AbonementService {
    @Autowired
    private AbonementRepository abonementRepository;
    @Autowired
    private DocumentsRepository documentsRepository;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private DocumentGenerator documentGenerator;

    public boolean hasAbonement(int clientId,int productId) {
        List<AbonementEntity> clientAbonements = abonementRepository.findByClientId(clientId);
        for(AbonementEntity abonementEntity : clientAbonements) {
            if(abonementEntity.getProductId() == productId && (!abonementEntity.getStatus().equals("finished"))){
                return true;
            }
        }
        return false;
    }

    public void appointAbonement(int clientId,int productId,String html) {
        //находим название продукта
        DocumentsEntity clientContract = new DocumentsEntity();
        ProductsEntity product = productsRepository.findById(productId).get();
        documentGenerator.generateAbonement(html,clientId,product,clientContract);
        documentsRepository.save(clientContract);
        //Реализовать сохранение документы и ее реализацию TODO
        AbonementEntity clientAbonement = new AbonementEntity();
        clientAbonement.setClientId(clientId);
        clientAbonement.setProductId(productId);
        clientAbonement.setCreatedDate(LocalDateTime.now());
        clientAbonement.setStatus("notStarted");
        clientAbonement.setDocumentId(clientContract.getId());
        abonementRepository.save(clientAbonement);


    }
}
