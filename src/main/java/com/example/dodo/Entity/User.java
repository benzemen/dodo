package com.example.dodo.Entity;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("dodo_db")
public class User {

    @Id
    private String id;

    @NotBlank(message="UserName is required")
    private String username;
    @NotBlank(message="Password is required")
    private String password;
    @NotBlank(message="UserEmail is required")
    private String useremail;

    private String provider;

}
