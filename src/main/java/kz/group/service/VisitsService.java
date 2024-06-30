package kz.group.service;

import kz.group.DTO.ClientInGym;
import kz.group.entity.AbonementEntity;
import kz.group.entity.ClientsEntity;
import kz.group.entity.VisitsEntity;
import kz.group.repository.AbonementRepository;
import kz.group.repository.ClientsRepository;
import kz.group.repository.VisitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VisitsService {
    @Autowired
    private VisitsRepository visitsRepository;
    @Autowired
    private AbonementRepository abonementRepository;
    @Autowired
    private ClientsRepository clientsRepository;

    public void enterTheGym(long clientId,long abonementId){
        VisitsEntity visitsEntity = new VisitsEntity();
        visitsEntity.setClientId(clientId);
        visitsEntity.setAbonementId(abonementId);
        visitsEntity.setVisitDate(LocalDateTime.now());
        AbonementEntity abonementEntity = abonementRepository.getById(abonementId);
        abonementEntity.setClientInGym(true);
        visitsRepository.save(visitsEntity);
    }

    public void exitTheGym(long clientId,long abonementId){
        AbonementEntity abonementEntity = abonementRepository.getById(abonementId);
        abonementEntity.setClientInGym(false);
        List<VisitsEntity> visitsEntities = visitsRepository.findByClientId(clientId);
        for (VisitsEntity visitsEntity : visitsEntities) {
            if(visitsEntity.getAbonementId() == abonementId){
                visitsEntity.setDepartureDate(LocalDateTime.now());
                visitsRepository.save(visitsEntity);
            }
        }
        abonementRepository.save(abonementEntity);
    }


    public LocalDateTime lastVisit(long clientId){
        List<VisitsEntity> clientVisits = visitsRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        VisitsEntity lastVisits = new VisitsEntity();
        for(VisitsEntity clientVisit : clientVisits){
            if(clientVisit.getClientId() == clientId){
                lastVisits = clientVisit;
            }
        }
        return lastVisits.getVisitDate();
    }

    public List<ClientInGym> getClientsInGym(){
        List<VisitsEntity> clientVisits = visitsRepository.findByVisitDateIsNotNullAndDepartureDateIsNull();
        List<ClientInGym> clientInGyms = new ArrayList<>();
        for(VisitsEntity clientVisit : clientVisits){
            Optional<ClientsEntity> client = clientsRepository.findById(clientVisit.getClientId());
            ClientInGym clientInGym = new ClientInGym();
            clientInGym.setFirstName(client.get().getFirstName());
            clientInGym.setLastName(client.get().getLastName());
            clientInGym.setPatronimyc(client.get().getPatronymic());
            clientInGym.setVisitDate(clientVisit.getVisitDate());
            clientInGym.setAbonementId(clientVisit.getAbonementId());
            clientInGym.setContactNumber(client.get().getContactNumber());
            clientInGym.setClientId(clientVisit.getClientId());
            clientInGym.setGender(client.get().getGender());
            clientInGyms.add(clientInGym);
        }
        return clientInGyms;
    }
}
