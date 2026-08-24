package utils;

import models.profile.Profile;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.extension.*;
import testdata.TestData;

public class TestDataGeneratorExtension implements BeforeEachCallback, ParameterResolver {
    private static final ExtensionContext.Namespace PROFILE_NAMESPACE = ExtensionContext.Namespace.create(Profile.class);
    private static final String PROFILE_NAMESPACE_KEY = "profile";
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Profile randomProfile = TestData.RANDOM_PROFILE;
        context.getStore(PROFILE_NAMESPACE).put(PROFILE_NAMESPACE_KEY, randomProfile);
        System.out.println("Generated random profile: " + randomProfile);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(Profile.class);
    }

    @Override
    public @Nullable Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(PROFILE_NAMESPACE).get(PROFILE_NAMESPACE_KEY, Profile.class);
    }
}
