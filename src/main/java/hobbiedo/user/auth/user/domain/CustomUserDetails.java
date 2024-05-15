package hobbiedo.user.auth.user.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import hobbiedo.user.auth.user.dto.request.LoginRequestDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
	private final LoginRequestDTO loginDTO;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new SimpleGrantedAuthority("ROLE_USER"));
		return collection;
	}

	@Override
	public String getPassword() {
		if (loginDTO == null) {
			return null;
		}
		return loginDTO.getPassword();
	}

	/* 여기서 말하는 username == uuid */
	@Override
	public String getUsername() {
		return loginDTO.getUuid();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
