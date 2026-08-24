package models.profile.update;

import lombok.Data;

@Data
public class PatchResponse {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String company;
}
