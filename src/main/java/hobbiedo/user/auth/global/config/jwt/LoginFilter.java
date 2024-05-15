package hobbiedo.user.auth.global.config.jwt;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import hobbiedo.user.auth.user.domain.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	private static String getRole(Authentication authResult) {
		return authResult
				.getAuthorities()
				.iterator()
				.next()
				.getAuthority();
	}

	/* 클라이언트가 인증 API를 쐈을 때 가장 먼저 트리거 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

		return authenticationManager.authenticate(authToken);
	}

	/* 로그인 성공시 실행됨 */
	@Override
	protected void successfulAuthentication(
			HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		CustomUserDetails userDetails = (CustomUserDetails)authResult.getPrincipal();
		String username = userDetails.getUsername();
		String role = getRole(authResult);

		String jwtToken = jwtUtil.createJwt(username, role, 60 * 60 * 10L);
		response.addHeader("Authorization", "Bearer " + jwtToken);

	}

	/* 로그인 실패시 실행됨 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		response.setStatus(HttpStatus.BAD_REQUEST.value());
	}
}
