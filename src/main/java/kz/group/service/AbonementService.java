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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        //генерируем  uuid
        UUID abonementUUID = UUID.randomUUID();
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
        clientAbonement.setUuid(abonementUUID);
        abonementRepository.save(clientAbonement);
    }

    public List<AbonementEntity> abonementList(long clientId) {
        List<AbonementEntity> clientAbonements = abonementRepository.findByClientId(clientId);
        return clientAbonements;
    }

    public void abonementStarted(long abonementId) {
        AbonementEntity clientAbonement = abonementRepository.findById(abonementId).get();
        clientAbonement.setStatus("started");
        clientAbonement.setStartDate(LocalDate.now());

        //нужно для получения сколько будет длится абонемент
        ProductsEntity product = productsRepository.findById(clientAbonement.getProductId()).get();
        clientAbonement.setEndDate(LocalDate.now().plusDays(product.getTotalClasses()));
        clientAbonement.setActiveDays(product.getTotalClasses());
        abonementRepository.save(clientAbonement);
    }

    public List<AbonementEntity> clientsInGym(){
        List<AbonementEntity> abonements = abonementRepository.findAll();
        List<AbonementEntity> clientsInGym = new ArrayList<>();
        for(AbonementEntity abonement : abonements) {
            if (abonement.getClientInGym()){
                clientsInGym.add(abonement);
            }
        }
        if(clientsInGym.isEmpty()){
            return null;
        }
        return clientsInGym;
    }
}
