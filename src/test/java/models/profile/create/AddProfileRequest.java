package models.profile.create;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddProfileRequest {
    private String name;
    private String email;
    private String password;
}
