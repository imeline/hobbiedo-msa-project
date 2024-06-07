package hobbiedo.region.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.global.base.BaseResponse;
import hobbiedo.global.status.SuccessStatus;
import hobbiedo.region.application.RegionService;
import hobbiedo.region.dto.request.RegionSingUpDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Tag(name = "활동 지역", description = "Region API")
@RequestMapping("/v1/non-users/region")
public class NonMemberRegionController {

	private final RegionService regionService;

	@Operation(summary = "회원가입 시, 활동 지역 등록", description = "회원가입 시, 회원의 활동 지역을 등록한다. (첫 활동 지역이므로, 기본 활동 지역으로 설정)")
	@PostMapping("/sign-up")
	public BaseResponse<Void> singUpRegion(@RequestBody RegionSingUpDTO regionSingUpDTO) {
		regionService.singUpRegion(regionSingUpDTO);
		return BaseResponse.onSuccess(SuccessStatus.CREATE_SING_UP_REGION, null);
	}
}
