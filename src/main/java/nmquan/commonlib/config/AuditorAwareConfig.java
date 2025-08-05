package nmquan.commonlib.config;

import nmquan.commonlib.model.JwtUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            JwtUser user = (JwtUser) authentication.getPrincipal();
            return Optional.of(user.getUsername());
        }
        return Optional.of("anonymousUser");
    }
}
