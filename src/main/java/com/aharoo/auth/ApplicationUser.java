package com.aharoo.auth;

import com.aharoo.security.ApplicationUserRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
@Getter
@Setter
public class ApplicationUser implements UserDetails {

	@SequenceGenerator(
			name = "app_user_sequence",
			sequenceName = "app_user_sequence",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "app_user_sequence"
	)
	Integer user_id;
	String username;
	String password;
	String email;
	@Enumerated(EnumType.STRING)
	ApplicationUserRole role;
	Boolean locked = false;
	Boolean enabled = false;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getGrantedAuthorities();
	}

	public String getEmail(){return email;}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {return true;}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
