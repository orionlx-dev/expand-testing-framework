package models.profile.fetch;

import lombok.Data;

@Data
public class FetchProfileResponse {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String company;
}
