package org.sergheimorari.lettersender.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Setter
@Getter
@Validated
@Configuration
@ConfigurationProperties(prefix = "letter-sender.aws.sns")
public class SnsConfiguration {

    @NotBlank(message = "SNS topic ARN must be configured")
    public String topicArn;
}
