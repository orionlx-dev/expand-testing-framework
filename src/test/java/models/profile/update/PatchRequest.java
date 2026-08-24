package models.profile.update;

import lombok.Data;

@Data
public class PatchRequest {
    private String name;
    private String phone;
    private String company;
}
