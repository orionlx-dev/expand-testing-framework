package testdata;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import models.profile.Profile;
import net.datafaker.Faker;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestData {
    private static final Faker FAKER = new Faker();

    public static Profile generateProfile() {
        return Profile.builder()
                .name(FAKER.name().name())
                .email(FAKER.internet().emailAddress())
                .password(FAKER.credentials().password())
                .phone(FAKER.phoneNumber().phoneNumber())
                .company(FAKER.company().name())
                .build();
    }

    public static final Profile RANDOM_PROFILE = Profile.builder()
            .name(FAKER.name().name())
            .email(FAKER.internet().emailAddress())
            .password(FAKER.credentials().password())
            .phone(FAKER.phoneNumber().phoneNumber())
            .company(FAKER.company().name())
            .build();

    public static final Profile DEFAULT_PROFILE = Profile.builder()
            .name("username")
            .email("email@mail.ru")
            .password("secretpass123")
            .phone("89005002050")
            .company("company")
            .build();
}
