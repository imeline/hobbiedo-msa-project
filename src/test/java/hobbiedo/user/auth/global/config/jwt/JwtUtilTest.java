package hobbiedo.user.auth.global.config.jwt;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hobbiedo.user.auth.user.domain.TokenType;

@SpringBootTest
class JwtUtilTest {
	@Autowired
	JwtUtil jwtUtil;

	private String createToken(String uuid, TokenType tokenType) {
		return jwtUtil.createJwt(uuid, tokenType);
	}

	@Test
	@DisplayName("액세스 토큰과 리프레시 토큰 발급에 성공해야한다.")
	void 토큰_발급_성공() {
		createToken("1234", TokenType.ACCESS_TOKEN);
		createToken("1234", TokenType.REFRESH_TOKEN);
	}

	@Test
	@DisplayName("토큰에서 동일한 uuid를 파싱한다면 성공한다.")
	void 토큰_uuid_파싱_성공() {
		String jwt1 = createToken("1234", TokenType.ACCESS_TOKEN);
		String jwt2 = createToken("1234", TokenType.REFRESH_TOKEN);
		assertThat(jwtUtil.getUuid(jwt1)).isEqualTo("1234");
		assertThat(jwtUtil.getUuid(jwt2)).isEqualTo("1234");
	}

	@Test
	void 토큰_타입_파싱_성공() {
		String jwt1 = createToken("1234", TokenType.ACCESS_TOKEN);
		String jwt2 = createToken("1234", TokenType.REFRESH_TOKEN);
		assertThat(jwtUtil.getTokenType(jwt1)).isEqualTo(TokenType.ACCESS_TOKEN);
		assertThat(jwtUtil.getTokenType(jwt2)).isEqualTo(TokenType.REFRESH_TOKEN);
	}
}