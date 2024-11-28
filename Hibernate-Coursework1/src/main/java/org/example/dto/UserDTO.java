package org.example.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    @Id
    private String userId;
    private String userName;
    private int telephone;
    private String email;
    private String password;

    public UserDTO(String userId, String userName, String email, String password, int telephone) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
    }
}
