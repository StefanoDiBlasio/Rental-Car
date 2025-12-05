package rental.car.demo.user.jwt.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rental.car.demo.user.domain.RoleType;
import rental.car.demo.user.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        this.enabled = user.getEnabled();

        this.authorities = Arrays.stream(user.getRoleType().toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return username;
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
