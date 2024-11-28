package org.example.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminDTO {
    @Id
    private String userId;
    private String userName;
    private int telephone;
    private String email;
    private String password;

    public AdminDTO(String userId, String userName, String email, String password, int telephone) {
        this.userId = userId;      // Initialize userId
        this.userName = userName;  // Initialize userName
        this.email = email;        // Initialize email
        this.password = password;  // Initialize password
        this.telephone = telephone; // Initialize telephone
    }
}
