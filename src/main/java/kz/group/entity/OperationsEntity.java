package kz.group.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity(name = "operation")
public class OperationsEntity {
    @Column
    @Id
    Long id;
    @Column(name = "date_from")
    Date dateFrom;
    @Column(name = "date_before")
    Date dateBefore;
    @Column(name = "create_date")
    Date createDate;
    @Column(name = "update_date")
    Date updateDate;
}
