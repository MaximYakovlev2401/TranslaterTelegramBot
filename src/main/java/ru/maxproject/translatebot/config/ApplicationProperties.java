package ru.maxproject.translatebot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mail")
@Data
public class ApplicationProperties {

    private String host;
    private String port;
    private String username;
    private String senderEmailPassword;
    private String from;
}
