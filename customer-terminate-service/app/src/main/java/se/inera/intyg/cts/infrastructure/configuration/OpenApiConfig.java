package se.inera.intyg.cts.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import java.util.Optional;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile("dev")
@Configuration
public class OpenApiConfig {

    private final BuildProperties buildProperties;

    public OpenApiConfig(Optional<BuildProperties> buildProperties) {
        this.buildProperties = buildProperties.orElse(null);
    }

    @Bean
    public OpenAPI ctsOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("CTS API")
                .description("Customer termination service - CTS")
                .version(buildProperties != null ? buildProperties.getVersion() : "unspecified")
            );
    }
}
