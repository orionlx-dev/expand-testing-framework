package tests.api.positive;

import annotations.FakeCompany;
import annotations.FakePhone;
import controllers.ProfileController;
import ctx.AuthContext;
import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import models.profile.Profile;
import models.profile.create.AddProfileRequest;
import models.profile.create.AddProfileResponse;
import models.profile.delete.DeleteResponse;
import models.profile.fetch.FetchProfileResponse;
import models.profile.login.LoginRequest;
import models.profile.login.LoginResponse;
import models.profile.update.PatchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.CsvSource;
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

    @DisplayName("Создание и удаление профиля DELETE /users/delete-account")
    @Severity(SeverityLevel.CRITICAL)
    @RepeatedTest(10)
    public void createAndDeleteFlowOfRandomProfileTest(Profile profile) {
        Allure.parameter("name", profile.getName());
        Allure.parameter("email", profile.getEmail());
        Allure.parameter("password", profile.getPassword());

        profileController.registerProfile(
                ProfileToRequestMapper.INSTANCE.profileToAddProfileRequestDto(profile)
        );
        profileController.loginProfile(
                ProfileToRequestMapper.INSTANCE.profileToLoginRequestDto(profile)
        );

        Response response = profileController.delete();
        DeleteResponse deleteResponse = response.as(DeleteResponse.class);

        assertEquals(200, response.statusCode());
        assertTrue(deleteResponse.getSuccess());
    }

    @DisplayName("Пользовательский флоу - создание, обновление, чтение и удаление профиля")
    @TmsLink("270")
    @Severity(SeverityLevel.CRITICAL)
    @RepeatedTest(10)
    public void createLoginFetchUpdateDeleteRandomProfileTest(Profile profile,
                                                              @FakePhone String phone,
                                                              @FakeCompany String company) {

        Allure.parameter("name", profile.getName());
        Allure.parameter("email", profile.getEmail());
        Allure.parameter("password", profile.getPassword());
        Allure.parameter("phone", profile.getPhone());
        Allure.parameter("company", profile.getCompany());

        AddProfileResponse registerResponse = profileController.registerProfile(
                ProfileToRequestMapper.INSTANCE.profileToAddProfileRequestDto(profile)
        ).jsonPath().getObject("data", AddProfileResponse.class);

        assertNotNull(registerResponse.getId());

        LoginResponse loginResponse = profileController.loginProfile(
                ProfileToRequestMapper.INSTANCE.profileToLoginRequestDto(profile)
        ).jsonPath().getObject("data", LoginResponse.class);

        Allure.parameter("token", loginResponse.getToken());

        assertNotNull(loginResponse.getToken());

        profile.setPhone(phone);
        profile.setCompany(company);
        Response response = profileController.patch(
                ProfileToRequestMapper.INSTANCE.profileToPatchRequestDto(profile)
        );
        PatchResponse patchResponse = response.jsonPath().getObject("data", PatchResponse.class);

        assertEquals(200, response.statusCode());
        assertEquals(profile.getName(), patchResponse.getName());
        assertEquals(profile.getPhone(), patchResponse.getPhone());
        assertEquals(profile.getCompany(), patchResponse.getCompany());

        DeleteResponse deleteResponse = profileController.delete().as(DeleteResponse.class);

        assertEquals(true, deleteResponse.getSuccess());
        assertEquals(200, deleteResponse.getStatus());
    }

}
