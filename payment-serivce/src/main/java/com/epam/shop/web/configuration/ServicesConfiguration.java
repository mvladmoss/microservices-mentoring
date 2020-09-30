package com.epam.shop.web.configuration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@EqualsAndHashCode
@ConfigurationProperties(prefix = "service")
@Configuration
public class ServicesConfiguration {
    private Hosts host;
    private Urls url;
    private RetrySetting retrySetting;

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class Hosts {
        private String customerModule;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class Urls {
        private String customerCreditCard;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class RetrySetting {
        private Integer maxAttempts;
        private Integer backoffInSeconds;
    }
}
