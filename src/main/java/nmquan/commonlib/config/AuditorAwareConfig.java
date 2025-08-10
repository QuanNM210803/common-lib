package nmquan.commonlib.config;

import nmquan.commonlib.utils.WebUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Objects;
import java.util.Optional;

public class AuditorAwareConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        String username = WebUtils.getCurrentUsername();
        return Optional.of(Objects.requireNonNullElse(username, "anonymous"));
    }
}
