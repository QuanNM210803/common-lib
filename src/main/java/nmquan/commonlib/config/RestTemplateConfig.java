package nmquan.commonlib.config;

import nmquan.commonlib.constant.CommonConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Value("${jwt.x-internal-token}")
    private String X_INTERNAL_TOKEN;

    @Bean(name = CommonConstants.INTERNAL)
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add((request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            if (!headers.containsKey(CommonConstants.X_INTERNAL_TOKEN)) {
                headers.set(CommonConstants.X_INTERNAL_TOKEN, X_INTERNAL_TOKEN);
            }
            return execution.execute(request, body);
        });

        return restTemplate;
    }

    @Bean(name = CommonConstants.EXTERNAL)
    public RestTemplate externalRestTemplate() {
        return new RestTemplate();
    }
}
