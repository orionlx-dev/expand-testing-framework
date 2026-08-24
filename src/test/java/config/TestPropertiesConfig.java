package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/${env}.properties",
        "classpath:config/default.properties"
})
public interface TestPropertiesConfig extends Config {

    @Key("API_BASE_URL")
    String getApiBaseUrl();

    @Key("API_REQUEST_TIMEOUT")
    Long getRequestTimeout();

}
