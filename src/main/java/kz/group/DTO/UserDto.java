package kz.group.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class UserDto {
    @NotEmpty(message = "Это имя не подходит")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
