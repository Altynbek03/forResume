package kz.group.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;

@Data
@Entity(name = "payments")
public class PaymentsEntity {
    @Id
    @Column
    Long id;
    @Column(name = "payment_date")
    Date paymentDate;
    @Column(name = "total_cost")
    Long totalCost;
    @Column(name = "create_date")
    Date createDate;
    @Column(name = "update_date")
    Date updateDate;
}
