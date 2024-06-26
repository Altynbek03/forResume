package kz.group.service;

import kz.group.entity.AbonementEntity;
import kz.group.entity.DocumentsEntity;
import kz.group.repository.DocumentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentsService {
    @Autowired
    private DocumentsRepository documentsRepository;

    public List<DocumentsEntity> findByClientId(int clientId) {
        return documentsRepository.findByClientId(clientId);
    }

    public boolean hasAgreement(int clienId) {
        List<DocumentsEntity> documents = documentsRepository.findByClientId(clienId);
        for(DocumentsEntity document : documents) {
            document.getContractType().equals("agreement");
            return true;
        }
        return false;
    }

    public String clientAgreementFileName(int clienId) {
        List<DocumentsEntity> documents = documentsRepository.findByClientId(clienId);
        for(DocumentsEntity document : documents) {
            document.getContractType().equals("agreement");
            return document.getContractFileName();
        }
        return null;
    }

}
