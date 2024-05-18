package hobbiedo.user.auth.user.domain;

import java.util.Arrays;
import java.util.Optional;

public enum TokenType {
	ACCESS_TOKEN("accessToken", null),
	REFRESH_TOKEN("refreshToken", null);
	private final String name;
	private Long expireTime;

	TokenType(String name, Long expireTime) {
		this.name = name;
		this.expireTime = expireTime;
	}

	public static Optional<TokenType> getByName(String name) {
		return Arrays.stream(TokenType.values())
				.filter(tokenType -> tokenType.name.equals(name))
				.findFirst();
	}

	public String getName() {
		return name;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}
}
