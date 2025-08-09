package nmquan.commonlib.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
public class JwtUser extends User {
    Map<String, Object> user;

    public JwtUser(Map<String, Object> user, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.user = user;
    }

    public JwtUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public JwtUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
