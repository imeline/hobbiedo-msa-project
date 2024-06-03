package hobbiedo.user.auth.member.presentation;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.user.auth.global.api.ApiResponse;
import hobbiedo.user.auth.global.api.code.status.SuccessStatus;
import hobbiedo.user.auth.member.application.AuthService;
import hobbiedo.user.auth.member.application.MemberService;
import hobbiedo.user.auth.member.dto.request.UpdatePasswordDTO;
import hobbiedo.user.auth.member.vo.request.UpdatePasswordVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Tag(name = "Login-Members", description = "로그인한 사용자가 이용할 수 있는 서비스")
public class LoginMemberController {
	private final AuthService authService;
	private final MemberService memberService;

	@PostMapping("/logout")
	@Operation(summary = "사용자 로그아웃",
		description = "리프레시 토큰을 삭제하는 것으로 사용자를 로그아웃 시킵니다.")
	public ApiResponse<Void> logoutApi(@RequestHeader(name = "Uuid") String uuid) {
		authService.logout(uuid);
		return ApiResponse.onSuccess(
			SuccessStatus.LOGOUT_SUCCESS,
			null
		);
	}

	@PatchMapping("/password")
	@Operation(summary = "사용자 비밀번호 재설정",
		description = "사용자의 비밀번호를 재설정합니다.")
	public ApiResponse<Void> updatePasswordApi(
		@RequestHeader(name = "Uuid") String uuid,
		@Valid @RequestBody UpdatePasswordVO updatePasswordVO) {
		UpdatePasswordDTO passwordDTO = UpdatePasswordDTO.builder()
			.uuid(uuid)
			.currentPassword(updatePasswordVO.getCurrentPassword())
			.newPassword(updatePasswordVO.getNewPassword())
			.build();

		memberService.update(passwordDTO);
		return ApiResponse.onSuccess(
			SuccessStatus.UPDATE_PASSWORD_SUCCESS,
			null
		);
	}
}
