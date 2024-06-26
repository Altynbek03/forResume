package kz.group.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "documents")
public class DocumentsEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "client_id")
    private int clientId;
    @Column(name = "contract_file_name")
    private String contractFileName;
    @Column(name = "contract_type")
    private String contractType;
    @Column(name = "create_date")
    private LocalDateTime createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getContractFileName() {
        return contractFileName;
    }

    public void setContractFileName(String contractFileName) {
        this.contractFileName = contractFileName;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
