package hobbiedo.user.auth.email.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailCheckDTO {
	private final String email;
	private final String authCode;
}
