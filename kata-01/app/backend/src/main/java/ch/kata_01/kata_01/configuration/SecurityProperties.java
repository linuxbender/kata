package ch.kata_01.kata_01.configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app.security")
@Component
@Getter
@Setter
@Validated
public class SecurityProperties {

    @NotBlank(message = "Security secret must not be blank")
    private String secret;
}
