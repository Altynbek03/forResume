
package kz.group.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity(name = "clients")
public class ClientsEntity {
    @Id
    @Column
    Long id;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    @Column
    String patronymic;
    @Column(name = "born_date")
    Date bornDate;
    @Column(name = "contact_number")
    String contactNumber;
    @Column
    String email;
    @Column
    boolean agreement;
    @Column(name = "create_date")
    Date createDate;
    @Column(name = "update_date")
    Date updateDate;
    @Column
    int gender;
}
