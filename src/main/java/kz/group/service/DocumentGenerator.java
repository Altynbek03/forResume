package kz.group.service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import kz.group.entity.ClientsEntity;
import kz.group.entity.DocumentsEntity;
import kz.group.entity.ProductsEntity;
import kz.group.repository.ClientsRepository;
import kz.group.repository.DocumentsRepository;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class DocumentGenerator {
    private final ClientsRepository clientsRepository;
    private final DocumentsRepository documentsRepository;
    private final ProductsService productsService;

    public DocumentGenerator(ClientsRepository clientsRepository, DocumentsRepository documentsRepository, ProductsService productsService) {
        this.clientsRepository = clientsRepository;
        this.documentsRepository = documentsRepository;
        this.productsService = productsService;
    }

    public String generateDocument(String html, ClientsEntity client) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfWriter pdfWriter = new PdfWriter(outputStream);
            DefaultFontProvider defaultFontProvider = new DefaultFontProvider();
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(defaultFontProvider);
            HtmlConverter.convertToPdf(html,pdfWriter, converterProperties);
            String random = UUID.randomUUID().toString();

            //Для пользовательского соглашения
            String uploadDir = "C:/Users/Алтынбек/Desktop/startup/public/agreements/";
            String fileName = random + ".pdf";
            String fullPath = uploadDir + fileName;
            FileOutputStream fout = new FileOutputStream(fullPath);


            //Сохраняем в базе документов
            DocumentsEntity documentsEntity = new DocumentsEntity();
            documentsEntity.setContractFileName(fileName);
            documentsEntity.setClientId(client.getId());
            documentsEntity.setContractType("agreement");
            documentsEntity.setCreateDate(LocalDateTime.now());
            documentsRepository.save(documentsEntity);
            outputStream.writeTo(fout);
            outputStream.close();

            outputStream.flush();
            fout.close();

            return null;
        } catch (Exception exception){

        }
        return null;
    }

    public String generateAbonement(String html, int clientId, ProductsEntity product,DocumentsEntity clientContract) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfWriter pdfWriter = new PdfWriter(outputStream);
            DefaultFontProvider defaultFontProvider = new DefaultFontProvider();
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(defaultFontProvider);
            HtmlConverter.convertToPdf(html,pdfWriter, converterProperties);
            String random = UUID.randomUUID().toString();

            //Для абонемента
            String uploadDir = "C:/Users/Алтынбек/Desktop/startup/public/abonements/";
            String fileName = random + ".pdf";
            String fullPath = uploadDir + fileName;
            FileOutputStream fout = new FileOutputStream(fullPath);


            //Сохраняем в базе документов

            clientContract.setContractFileName(fileName);
            clientContract.setClientId(clientId);
            clientContract.setContractType(product.getProductName());
            clientContract.setCreateDate(LocalDateTime.now());
            outputStream.writeTo(fout);
            outputStream.close();

            outputStream.flush();
            fout.close();

            return null;
        } catch (Exception exception){

        }
        return null;
    }
}
