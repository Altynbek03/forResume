package kz.group.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "clientVisits")
public class VisitsEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "visit_date")
    private LocalDateTime visitDate;

    @Column(name = "departure_date")
    private LocalDateTime departureDate;

    @Column(name = "abonement_id")
    private Long abonementId;
}
