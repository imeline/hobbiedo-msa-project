package hobbiedo.user.auth.global.config.jwt;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hobbiedo.user.auth.global.api.ApiResponse;
import hobbiedo.user.auth.global.api.code.status.ErrorStatus;
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
		String uuid = obtainUsername(request);
		String password = obtainPassword(request);
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(uuid, password);

		return authenticationManager.authenticate(authToken);
	}

	/* 로그인 성공시 실행됨 */
	@Override
	protected void successfulAuthentication(
			HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		CustomUserDetails userDetails = (CustomUserDetails)authResult.getPrincipal();
		String uuid = userDetails.getUsername();
		String role = getRole(authResult);

		String jwtToken = jwtUtil.createJwt(uuid, role, 60 * 60 * 10L);
		response.addHeader("Authorization", "Bearer " + jwtToken);
	}

	/* 로그인 실패시 실행됨 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		PrintWriter writer = response.getWriter();
		ErrorStatus loginException = ErrorStatus.USER_LOGIN_FAIL_EXCEPTION;

		response.setStatus(loginException.getHttpStatus().value());
		response.setContentType("application/json; charset=UTF-8");

		writer.write(toJson(ErrorStatus.USER_LOGIN_FAIL_EXCEPTION));
		writer.flush();
		writer.close();
	}

	private String toJson(ErrorStatus error) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		ApiResponse<Object> response = ApiResponse.onFailure(
				ErrorStatus.USER_LOGIN_FAIL_EXCEPTION.getStatus(),
				ErrorStatus.USER_LOGIN_FAIL_EXCEPTION.getMessage(),
				null);

		return objectMapper.writeValueAsString(response);
	}
}
