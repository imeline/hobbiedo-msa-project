package hobbiedo.user.auth.global.config.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {
	public static final String TOKEN_PREFIX = "Bearer ";
	private final SecretKey secretKey;

	public String getUuid(String token) {
		token = token.substring(TOKEN_PREFIX.length());
		return getJwtParser()
				.parseSignedClaims(token)
				.getPayload()
				.get("uuid", String.class);
	}

	public Boolean isExpired(String token) {
		token = token.substring(TOKEN_PREFIX.length());
		return getJwtParser()
				.parseSignedClaims(token)
				.getPayload()
				.getExpiration()
				.before(new Date());
	}

	public TokenType getTokenType(String token) {
		token = token.substring(TOKEN_PREFIX.length());
		String tokenType = getJwtParser()
				.parseSignedClaims(token)
				.getPayload()
				.get("tokenType", String.class);
		return TokenType
				.getByName(tokenType)
				.orElseThrow(RuntimeException::new);
	}

	public String createJwt(String uuid, TokenType tokenType) {
		String jwtToken = Jwts
				.builder()
				.claim("tokenType", tokenType.getName())
				.claim("uuid", uuid)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + tokenType.getExpireTime()))
				.signWith(secretKey)
				.compact();
		return TOKEN_PREFIX + jwtToken;
	}

	private JwtParser getJwtParser() {
		return Jwts
				.parser()
				.verifyWith(secretKey)
				.build();
	}
}
