package models.profile.create;

import lombok.Data;

@Data
public class AddProfileResponse {
    private String id;
    private String name;
    private String email;
}
