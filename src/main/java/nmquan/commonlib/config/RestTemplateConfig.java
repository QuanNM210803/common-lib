package nmquan.commonlib.config;

import nmquan.commonlib.constant.CommonConstants;
import nmquan.commonlib.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Value("${jwt.secret-internal}")
    private String SECRET_INTERNAL_KEY;

    @Bean(name = CommonConstants.INTERNAL)
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add((request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            String tokenInternal = JwtUtils.generateTokenInternal(SECRET_INTERNAL_KEY);
            headers.set("Authorization", tokenInternal);
            return execution.execute(request, body);
        });

        return restTemplate;
    }

    @Bean(name = CommonConstants.EXTERNAL)
    public RestTemplate externalRestTemplate() {
        return new RestTemplate();
    }
}
