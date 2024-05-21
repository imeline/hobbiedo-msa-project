package hobbiedo.user.auth.user.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import hobbiedo.user.auth.global.api.exception.handler.MemberExceptionHandler;
import hobbiedo.user.auth.user.domain.RefreshToken;
import hobbiedo.user.auth.user.dto.request.LoginRequestDTO;
import hobbiedo.user.auth.user.infrastructure.MemberRepository;
import hobbiedo.user.auth.user.infrastructure.RefreshTokenRepository;
import hobbiedo.user.auth.user.vo.response.LoginResponseVO;

@SpringBootTest
@ActiveProfiles("test")
@Sql("/schema.sql")
class AuthServiceTest {
	@Autowired
	private AuthService authService;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	@Autowired
	private MemberRepository memberRepository;

	private LoginRequestDTO createDTO(String loginId, String password) {
		return LoginRequestDTO.builder()
				.loginId(loginId)
				.password(password)
				.build();
	}

	@Test
	@DisplayName("아이디와 비밀번호가 모두 다를 경우, UserExceptionHandler 예외를 반환한다.")
	void 로그인_아이디_비밀번호_다를때_실패_테스트() {
		LoginRequestDTO fail = createDTO("failId", "failPassw123ord");
		Assertions.assertThatThrownBy(
						() -> authService.login(fail))
				.isInstanceOf(MemberExceptionHandler.class);
	}

	@Test
	@DisplayName("아이디만 다를 경우, UserExceptionHandler 예외를 반환한다.")
	void 로그인_아이디_다를때_실패_테스트() {
		LoginRequestDTO fail = createDTO("1234", "faillPwd");
		Assertions.assertThatThrownBy(
						() -> authService.login(fail))
				.isInstanceOf(MemberExceptionHandler.class);
	}

	@Test
	@DisplayName("아이디와 비밀번호가 같을 경우, 성공한다.")
	void 로그인_비밀번호_다를때_성공_테스트() {
		LoginRequestDTO success = createDTO("1234", "1234");
		authService.login(success);
	}

	@Test
	@DisplayName("저장한 리프레시 토큰이, refreshTokenRepository에서 찾은 것과 일치하면 성공한다.")
	void 로그인_레디스_저장() {
		LoginRequestDTO success = createDTO("1234", "1234");
		LoginResponseVO login = authService.login(success);
		String uuid = memberRepository.findByLoginId(success.getLoginId()).get().getUuid();
		RefreshToken findRefreshToken = refreshTokenRepository.findById(uuid).get();

		Assertions.assertThat(login.getRefreshToken()).isEqualTo(findRefreshToken.getRefresh());
	}
}