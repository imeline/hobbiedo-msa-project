package hobbiedo.user.auth.user.converter;

import hobbiedo.user.auth.user.dto.request.LoginRequestDTO;
import hobbiedo.user.auth.user.dto.request.ReIssueRequestDTO;
import hobbiedo.user.auth.user.vo.request.LoginRequestVO;
import hobbiedo.user.auth.user.vo.request.ReIssueRequestVO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthConverter {
	public static LoginRequestDTO toRequestDTO(LoginRequestVO loginVO) {
		return LoginRequestDTO.builder()
				.loginId(loginVO.getLoginId())
				.password(loginVO.getPassword())
				.build();
	}

	public static ReIssueRequestDTO toRequestDTO(ReIssueRequestVO reIssueVO) {
		return ReIssueRequestDTO.builder()
				.refreshToken(reIssueVO.getRefreshToken())
				.build();
	}
}
