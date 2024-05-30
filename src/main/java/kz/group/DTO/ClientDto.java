package kz.group.DTO;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;


public class ClientDto {
    @NotEmpty(message = "Имя не подходит")
    private String firstName;
    @NotEmpty(message = "Фамилия не подходит")
    private String lastName;
    @NotEmpty(message = "Отчество не подходит")
    private String patronymic;
    @NotEmpty(message = "Номер не подходит")
    private String contactNumber;
    @NotEmpty(message = "Почта не подходит")
    private String email;

    private String gender;
    private MultipartFile imageFile;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
