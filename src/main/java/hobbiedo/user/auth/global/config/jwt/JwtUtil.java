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

	public JwtUtil(@Value("${spring.jwt.secret-key}") String secret) {
		String signature = Jwts.SIG
				.HS256
				.key()
				.build()
				.getAlgorithm();

		secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), signature);
	}

	public String getUuid(String token) {

		return getJwtParser()
				.parseSignedClaims(token)
				.getPayload()
				.get("uuid", String.class);
	}

	public Boolean isExpired(String token) {

		return getJwtParser()
				.parseSignedClaims(token)
				.getPayload()
				.getExpiration()
				.before(new Date());
	}

	public String createJwt(String uuid, Long expiredMs) {

		String jwtToken = Jwts
				.builder()
				.claim("uuid", uuid)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expiredMs))
				.signWith(secretKey)
				.compact();
		
		return "Bearer " + jwtToken;
	}

	private JwtParser getJwtParser() {

		return Jwts
				.parser()
				.verifyWith(secretKey)
				.build();
	}
}
