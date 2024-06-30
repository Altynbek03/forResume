package kz.group.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

@Data
public class ClientDto {
    @NotEmpty(message = "Имя не подходит")
    private String firstName;
    @NotEmpty(message = "Фамилия не подходит")
    private String lastName;
//    @NotEmpty(message = "Отчество не подходит")
    private String patronymic;
    @NotEmpty(message = "Номер не подходит")
    private String contactNumber;
//    @NotEmpty(message = "Почта не подходит")
    private String email;

    private String gender;
    private MultipartFile imageFile;
    @NotNull(message = "Вы не выбрали дату рождения")
    private LocalDate dateOfBorn;

}
