package models.profile.login;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
