package hobbiedo.user.auth.global.config.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {

	private final SecretKey secretKey;

	public JwtUtil(
			@Value("${spring.jwt.secret-key}") String raw) {
		String signature = Jwts.SIG
				.HS256
				.key()
				.build()
				.getAlgorithm();

		secretKey = new SecretKeySpec(raw.getBytes(StandardCharsets.UTF_8), signature);
	}

	public String getUsername(String token) {

		return getJwtParser()
				.parseSignedClaims(token)
				.getPayload()
				.get("username", String.class);
	}

	public Boolean isExpired(String token) {

		return getJwtParser()
				.parseSignedClaims(token)
				.getPayload()
				.getExpiration()
				.before(new Date());
	}

	public String createJwt(String username, String role, Long expiredMs) {

		return Jwts
				.builder()
				.claim("username", username)
				.claim("role", role)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expiredMs))
				.signWith(secretKey)
				.compact();
	}

	private JwtParser getJwtParser() {

		return Jwts
				.parser()
				.verifyWith(secretKey)
				.build();
	}
}
