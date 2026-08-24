package tests;

import models.profile.Profile;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.TestDataGeneratorExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({TestDataGeneratorExtension.class})
public class UnitTest {

    @RepeatedTest(10)
    public void shouldGenerateProfileViaExtensionProperly(Profile profile) {
        System.out.println(profile);
        assertNotNull(profile);
        assertNull(profile.getId());
    }

}
