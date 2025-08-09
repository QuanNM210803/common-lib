package nmquan.commonlib.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {
    @Value("${open-api.url-gateway-localhost}")
    private String URL_GATEWAY_LOCALHOST;
    @Value("${open-api.url-localhost}")
    private String URL_LOCALHOST;
    @Value("${open-api.url-gateway-radmin}")
    private String URL_GATEWAY_RADMIN;
    @Value("${open-api.url-radmin}")
    private String URL_RADMIN;
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url(URL_GATEWAY_LOCALHOST),
                        new Server().url(URL_GATEWAY_RADMIN),
                        new Server().url(URL_LOCALHOST),
                        new Server().url(URL_RADMIN)
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ));
    }

}
