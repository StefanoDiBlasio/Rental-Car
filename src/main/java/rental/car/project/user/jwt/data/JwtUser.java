package rental.car.project.user.jwt.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rental.car.project.user.domain.RoleType;
import rental.car.project.user.domain.User;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
public class JwtUser implements UserDetails {

    private static final long serialVersionUID = 3424931433506682261L;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private RoleType currentRole;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;

    public JwtUser(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enabled = Boolean.TRUE.equals(user.getEnabled());

        this.authorities = Stream.of(user.getRoleType().toString())
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
