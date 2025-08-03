package nmquan.commonlib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class WebConfig implements WebMvcConfigurer {

    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareConfig();
    }
}
