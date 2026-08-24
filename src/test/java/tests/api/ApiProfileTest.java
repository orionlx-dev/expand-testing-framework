package tests.api;

import controllers.ProfileController;
import ctx.AuthContext;
import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import models.profile.Profile;
import models.profile.create.AddProfileRequest;
import models.profile.create.AddProfileResponse;
import models.profile.fetch.FetchProfileResponse;
import models.profile.login.LoginRequest;
import models.profile.login.LoginResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.TestDataGeneratorExtension;
import utils.mappers.ProfileToRequestMapper;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({TestDataGeneratorExtension.class})
public class ApiProfileTest {

    ProfileController profileController = new ProfileController(new AuthContext());

    @DisplayName("Регистрация нового пользователя /users/register")
    @Severity(SeverityLevel.CRITICAL)
    @RepeatedTest(10)
    public void registerRandomProfileTest(Profile profile) {
        AddProfileRequest request = ProfileToRequestMapper.INSTANCE.profileToAddProfileRequestDto(profile);
        Allure.parameter("name", request.getName());
        Allure.parameter("email", request.getEmail());
        Allure.parameter("password", request.getPassword());


        Response response = profileController.registerProfile(request);
        AddProfileResponse registerProfileResponse = response.jsonPath().getObject("data", AddProfileResponse.class);

        assertEquals(201, response.statusCode());
        assertNotNull(registerProfileResponse.getId());
        assertEquals(profile.getName(), registerProfileResponse.getName());
        assertEquals(profile.getEmail(), registerProfileResponse.getEmail());
    }

    @DisplayName("Аутентификация нового пользователя /users/login")
    @Severity(SeverityLevel.CRITICAL)
    @RepeatedTest(10)
    public void createAndLoginRandomProfileTest(Profile profile) {
        AddProfileRequest registerRequest = ProfileToRequestMapper.INSTANCE.profileToAddProfileRequestDto(profile);

        profileController.registerProfile(registerRequest);
        LoginRequest loginRequest = ProfileToRequestMapper.INSTANCE.profileToLoginRequestDto(profile);
        Allure.parameter("email", loginRequest.getEmail());
        Allure.parameter("password", loginRequest.getPassword());

        Response response = profileController.loginProfile(loginRequest);
        LoginResponse loginResponse = response.jsonPath().getObject("data", LoginResponse.class);
        Allure.parameter("token", loginResponse.getToken());

        assertEquals(200, response.statusCode());
        assertNotNull(loginResponse.getToken());
        assertEquals(profile.getEmail(), loginResponse.getEmail());
        assertEquals(profile.getName(), loginResponse.getName());
    }

    @DisplayName("Получение созданного профиля GET /users/profile")
    @Severity(SeverityLevel.CRITICAL)
    @RepeatedTest(10)
    public void createAndFetchRandomProfileTest(Profile profile) {
        profileController.registerProfile(ProfileToRequestMapper.INSTANCE.profileToAddProfileRequestDto(profile));
        LoginResponse loginResponse = profileController.loginProfile(
                ProfileToRequestMapper.INSTANCE.profileToLoginRequestDto(profile)
        ).jsonPath().getObject("data", LoginResponse.class);

        Response response = profileController.fetch();
        FetchProfileResponse fetchProfileResponse = response.jsonPath().getObject("data", FetchProfileResponse.class);

        Allure.parameter("email", profile.getEmail());
        Allure.parameter("password", profile.getPassword());
        Allure.parameter("token", loginResponse.getToken());

        assertEquals(200, response.statusCode());
        assertNotNull(fetchProfileResponse.getId());
    }

}
