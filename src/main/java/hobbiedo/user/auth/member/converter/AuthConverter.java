package hobbiedo.user.auth.member.converter;

import hobbiedo.user.auth.member.dto.request.LoginRequestDTO;
import hobbiedo.user.auth.member.dto.request.ReIssueRequestDTO;
import hobbiedo.user.auth.member.vo.request.LoginRequestVO;
import hobbiedo.user.auth.member.vo.request.ReIssueRequestVO;
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
