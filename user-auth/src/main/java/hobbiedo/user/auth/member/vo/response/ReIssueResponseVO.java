package hobbiedo.user.auth.member.vo.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReIssueResponseVO {
	private final String accessToken;
	private final String refreshToken;
}
