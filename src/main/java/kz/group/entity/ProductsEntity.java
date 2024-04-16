package kz.group.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity(name = "products")
public class ProductsEntity {
    @Column
    @Id
    Long id;
    @Column(name = "product_name")
    String productName;
    @Column
    Long days;
    @Column(name = "create_date")
    Date createDate;
    @Column(name = "update_date")
    Date updateDate;
    @Column(name = "total_classes")
    Long totalClasses;

}
