package hobbiedo.user.auth.user.application;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hobbiedo.user.auth.user.domain.CustomUserDetails;
import hobbiedo.user.auth.user.dto.request.LoginRequestDTO;
import hobbiedo.user.auth.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		LoginRequestDTO user = userRepository
				.findUserByUsername(username)
				.orElse(null);

		if (user == null) {
			return null;
		}
		return new CustomUserDetails(user);
	}
}
