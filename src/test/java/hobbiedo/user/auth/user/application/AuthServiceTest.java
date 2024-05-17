package hobbiedo.user.auth.user.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import hobbiedo.user.auth.global.api.exception.handler.MemberExceptionHandler;
import hobbiedo.user.auth.global.config.jwt.JwtUtil;
import hobbiedo.user.auth.user.infrastructure.MeberRepository;
import hobbiedo.user.auth.user.vo.request.LoginRequestVO;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private MeberRepository meberRepository;

	@Mock
	private BCryptPasswordEncoder encoder;

	@InjectMocks
	private AuthService authService;

	@Test
	@DisplayName("아이디와 비밀번호가 모두 다를 경우, UserExceptionHandler 예외를 반환한다.")
	void 로그인_아이디_비밀번호_다를때_실패_테스트() {
		LoginRequestVO fail = new LoginRequestVO("failId", "failPassword");
		Assertions.assertThatThrownBy(
						() -> authService.login(fail))
				.isInstanceOf(MemberExceptionHandler.class);
	}

	@Test
	@DisplayName("아이디만 다를 경우, UserExceptionHandler 예외를 반환한다.")
	void 로그인_아이디_다를때_실패_테스트() {
		LoginRequestVO fail = new LoginRequestVO("1234", "faillPwd");
		Assertions.assertThatThrownBy(
						() -> authService.login(fail))
				.isInstanceOf(MemberExceptionHandler.class);
	}

	@Test
	@DisplayName("아이디와 비밀번호가 같을 경우, 성공한다.")
	void 로그인_비밀번호_다를때_실패_테스트() {
		LoginRequestVO fail = new LoginRequestVO("1234", "1234");
		Assertions.assertThatThrownBy(
						() -> authService.login(fail))
				.isInstanceOf(MemberExceptionHandler.class);
	}
}