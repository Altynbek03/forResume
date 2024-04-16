package kz.group.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "users")
public class UsersEntity {
    @Id
    @Column
    private Long id;
    @Column
    private String username;
    @Column(name="password_hash")
    private String passwordHash;
    @Column(name="create_date")
    private LocalDateTime createDate;
    @Column
    private Boolean active;
}
