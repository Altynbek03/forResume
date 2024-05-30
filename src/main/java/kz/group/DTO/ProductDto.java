package kz.group.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ProductDto {
    @NotEmpty(message = "Это имя не подходит")
    private String name;
    @Min(0)
    private double price;
    @Size(min = 10,message = "Минимально должно быть 10 символов")
    @Size(max = 500,message = "Максимально может быть 500 символов")
    private String description;
    @Min(7)
    private int totalClasses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }
}
