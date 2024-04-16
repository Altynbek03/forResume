package kz.group.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity(name = "abonnement")
public class SubscriptionEntity {
    @Id
    @Column
    Long id;
    @Column
    String passes;
    @Column(name = "coach_id")
    Long coachId;
    @Column(name = "create_date")
    Date createDate;
    @Column(name = "update_date")
    Date updateDate;
    @Column(name = "total_classes")
    int totalClasses;
}
