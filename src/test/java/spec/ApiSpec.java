package spec;

import config.TestPropertiesConfig;
import ctx.AuthContext;
import filters.AuthFilter;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.aeonbits.owner.ConfigFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiSpec {

    static TestPropertiesConfig config = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    public static RequestSpecification unauthenticatedSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(config.getApiBaseUrl())
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }

    public static RequestSpecification authenticatedSpec(AuthContext authContext) {
        return new RequestSpecBuilder()
                .addRequestSpecification(unauthenticatedSpec())
                .addFilter(new AuthFilter(authContext))
                .build();
    }

}
