package kz.group.service;

import kz.group.entity.DocumentsEntity;
import kz.group.repository.DocumentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentsService {
    @Autowired
    private DocumentsRepository documentsRepository;

    public List<DocumentsEntity> findByClientId(int clientId) {
        return documentsRepository.findByClientId(clientId);
    }

    public Optional<DocumentsEntity> findById(long documentId) {
        return documentsRepository.findById(documentId);
    }

    public boolean hasAgreement(long clienId) {
        List<DocumentsEntity> documents = documentsRepository.findByClientId(clienId);
        for(DocumentsEntity document : documents) {
            document.getContractType().equals("agreement");
            return true;
        }
        return false;
    }

    public String clientAgreementFileName(long clienId) {
        List<DocumentsEntity> documents = documentsRepository.findByClientId(clienId);
        for(DocumentsEntity document : documents) {
            document.getContractType().equals("agreement");
            return document.getContractFileName();
        }
        return null;
    }

    public List<DocumentsEntity> clientAbonementContracts(int clientId,List<DocumentsEntity> documents){
        List<DocumentsEntity> abonements = new ArrayList<>();
        for(DocumentsEntity document : documents) {
            if((!document.getContractType().equals("agreement"))&& document.getClientId()==clientId) {
                abonements.add(document);
            }
        }
        return abonements;
    }



}
