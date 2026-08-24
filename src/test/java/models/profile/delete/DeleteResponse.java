package models.profile.delete;

import lombok.Data;

@Data
public class DeleteResponse {
    private Boolean success;
    private Integer status;
    private String message;
}
