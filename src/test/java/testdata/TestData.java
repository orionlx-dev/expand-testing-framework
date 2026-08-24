package testdata;

import annotations.FakeCompany;
import annotations.FakePhone;
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

    public static String generatePhone(FakePhone annotation) {
        String phone;

        do {
            phone = FAKER.phoneNumber()
                    .phoneNumber()
                    .replaceAll("\\D", "");
        } while (
                phone.length() < annotation.minLength()
                        || phone.length() > annotation.maxLength()
        );

        return phone;
    }
    public static String generateCompany(FakeCompany annotation) {
        for (int attempt = 0; attempt < 100; attempt++) {
            String company = FAKER.company().name();

            if (company.length() >= annotation.minLength()
                    && company.length() <= annotation.maxLength()) {
                return company;
            }
        }

        throw new IllegalStateException(
                "Unable to generate company with length between "
                        + annotation.minLength()
                        + " and "
                        + annotation.maxLength()
        );
    }
}
