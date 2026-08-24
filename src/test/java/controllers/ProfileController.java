package controllers;

import config.TestPropertiesConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.profile.Profile;
import models.profile.create.AddProfileRequest;
import org.aeonbits.owner.ConfigFactory;

import static io.restassured.RestAssured.*;

public final class ProfileController {

    RequestSpecification requestSpecification;
    private static final String PROFILE_ENDPOINT = "users";
    TestPropertiesConfig config = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    public ProfileController() {
        requestSpecification = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .baseUri(config.getApiBaseUrl());
    }

    public Response registerProfile(AddProfileRequest profile) {
        return given()
                .spec(this.requestSpecification)
                .body(profile)
                .when()
                .post(PROFILE_ENDPOINT + "/" + "register")
                .andReturn();

    }

}
