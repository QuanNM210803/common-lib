package nmquan.commonlib.model;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UserCustom extends User {
    private Long userId;
    private List<String> roles;
    private String email;
    private String fullName;
    private Claims payload;

    public UserCustom(Long userId, List<String> roles, String email, String fullName, Claims payload,
                      String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.roles = roles;
        this.email = email;
        this.fullName = fullName;
        this.payload = payload;
    }

    public UserCustom(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserCustom(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
