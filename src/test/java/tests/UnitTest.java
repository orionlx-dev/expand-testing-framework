package tests;

import config.TestPropertiesConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {

    @Test
    public void shouldOperateTestConfigurationProperly() {
        String baseUrl = "https://practice.expandtesting.com/notes/api";
        Long requestTimeout = 10000L;
        TestPropertiesConfig config = ConfigFactory.create(TestPropertiesConfig.class);

        assertEquals(baseUrl, config.getApiBaseUrl());
        assertEquals(requestTimeout, config.getRequestTimeout());
    }

}
