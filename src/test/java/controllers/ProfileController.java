package controllers;

import ctx.AuthContext;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import models.profile.create.AddProfileRequest;
import models.profile.login.LoginRequest;
import spec.ApiSpec;

import static io.restassured.RestAssured.given;

@RequiredArgsConstructor
public final class ProfileController {

    private final AuthContext authContext;
    private static final String PROFILE_ENDPOINT = "users";

    @Step("Создание нового профиля")
    public Response registerProfile(AddProfileRequest profile) {
        return given()
                .spec(ApiSpec.unauthenticatedSpec())
                .body(profile)
                .when()
                .post(PROFILE_ENDPOINT + "/" + "register")
                .andReturn();

    }

    @Step("Аутентификация пользователя")
    public Response loginProfile(LoginRequest request) {
        Response response = given()
                .spec(ApiSpec.unauthenticatedSpec())
                .body(request)
                .when()
                .post(PROFILE_ENDPOINT + "/" + "login")
                .andReturn();

        authContext.setToken(response.jsonPath().getString("data.token"));
        return response;
    }

    @Step("Получение аутентифицированного пользователя")
    public Response fetch() {
        return given()
                .spec(ApiSpec.authenticatedSpec(authContext))
                .when()
                .get(PROFILE_ENDPOINT + "/" + "profile")
                .andReturn();
    }

}
