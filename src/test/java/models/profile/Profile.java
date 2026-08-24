package models.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Profile {
    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String company;
}
