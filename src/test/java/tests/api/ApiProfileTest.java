package tests.api;

import controllers.ProfileController;
import io.restassured.response.Response;
import models.profile.Profile;
import models.profile.create.AddProfileRequest;
import models.profile.create.AddProfileResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.TestDataGeneratorExtension;
import utils.mappers.ProfileToRequestMapper;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({TestDataGeneratorExtension.class})
public class ApiProfileTest {

    ProfileController profileController = new ProfileController();

    @Test
    public void registerRandomProfileTest(Profile profile) {
        Response response = profileController.registerProfile(ProfileToRequestMapper.INSTANCE.profileToAddProfileRequestDto(profile));
        AddProfileResponse registerProfileResponse = response.jsonPath().getObject("data", AddProfileResponse.class);

        assertEquals(201, response.statusCode());
        assertNotNull(registerProfileResponse.getId());
        assertEquals(profile.getName(), registerProfileResponse.getName());
        assertEquals(profile.getEmail(), registerProfileResponse.getEmail());
    }


}
