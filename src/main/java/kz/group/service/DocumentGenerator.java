package kz.group.service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import kz.group.entity.ClientsEntity;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.UUID;


@Service
public class DocumentGenerator {
    public String generateDocument(String html, ClientsEntity client) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfWriter pdfWriter = new PdfWriter(outputStream);
            DefaultFontProvider defaultFontProvider = new DefaultFontProvider();
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(defaultFontProvider);
            HtmlConverter.convertToPdf(html,pdfWriter, converterProperties);
            String random = UUID.randomUUID().toString();
            FileOutputStream fout = new FileOutputStream("C:/Users/Алтынбек/Desktop/qwerty/"
                                                        + client.getFirstName() + " "
                                                        + client.getLastName() + " "
                                                        + client.getPatronymic()
                                                        + random + ".pdf");
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
