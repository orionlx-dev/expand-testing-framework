package models.profile.login;

import lombok.Data;

@Data
public class LoginResponse {
    private String id;
    private String email;
    private String name;
    private String token;
}
