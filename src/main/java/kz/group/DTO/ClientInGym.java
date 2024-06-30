package kz.group.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientInGym {
    private String firstName;
    private String lastName;
    private String patronimyc;
    private LocalDateTime visitDate;
    private Long clientId;
    private Long abonementId;
    private String contactNumber;
    private String gender;
}
