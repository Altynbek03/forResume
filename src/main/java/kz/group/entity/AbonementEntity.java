package kz.group.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "abonement")
public class AbonementEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "product_id")
    private int productId;
    @Column(name = "client_id")
    private int clientId;
    @Column(name = "document_id")
    private int documentId;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "status")
    private String status;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "active_days")
    private int activeDays;
    @Column(name = "UUID")
    private UUID uuid;
    @Column(name = "client_in_gym")
    private Boolean clientInGym;
}
