package com.example.dodo.DTO;

// DTO use hota hai taki hum loginrequest wali api mai body mai bheja huya data ache aur safe tareke se kara

public class LoginRequest {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
