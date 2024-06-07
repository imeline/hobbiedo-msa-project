package hobbiedo.region.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.global.base.BaseResponse;
import hobbiedo.global.status.SuccessStatus;
import hobbiedo.region.application.RegionService;
import hobbiedo.region.dto.request.RegionDetailDTO;
import hobbiedo.region.dto.response.RegionAddressNameDTO;
import hobbiedo.region.dto.response.RegionGetDetailDTO;
import hobbiedo.region.dto.response.RegionXyDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Tag(name = "활동 지역", description = "Region API")
@RequestMapping("/v1/users/region")
public class RegionController {

	private final RegionService regionService;

	@PostMapping
	@Operation(summary = "활동 지역 등록", description = "회원이 등록할 수 있는 활동 지역(동네)를 등록(추가)한다.")
	public BaseResponse<Void> addRegion(
		@RequestBody RegionDetailDTO regionDetailDTO, @RequestHeader(name = "Uuid") String uuid) {
		regionService.addRegion(regionDetailDTO, uuid);
		return BaseResponse.onSuccess(SuccessStatus.CREATE_REGION, null);
	}

	@Operation(summary = "활동 지역 이름명 리스트 조회", description = "한 회원에 대한 활동 지역(1~3개)의 이름명(읍동면) 리스트를 조회한다.")
	@GetMapping("/address-names")
	public BaseResponse<List<RegionAddressNameDTO>> getAddressNameList(
		@RequestHeader(name = "Uuid") String uuid) {
		return BaseResponse.onSuccess(SuccessStatus.FIND_ADDRESS_NAME_LIST,
			regionService.getAddressNames(uuid));
	}

	@Operation(summary = "한 활동 지역 정보 조회", description = "1개의 활동 지역에 대한 정보를 조회한다.")
	@GetMapping("/{regionId}")
	public BaseResponse<RegionGetDetailDTO> getRegionDetail(
		@PathVariable Long regionId) {
		return BaseResponse.onSuccess(SuccessStatus.FIND_REGION_DETAIL,
			regionService.getRegionDetail(regionId));
	}

	@Operation(summary = "기본 활동 지역 이름명 조회", description = "회원이 현재 기본으로 선택해놓은 활동 지역의 id,이름을 조회한다.")
	@GetMapping("/base-address-name")
	public BaseResponse<RegionAddressNameDTO> getBaseAddressName(
		@RequestHeader(name = "Uuid") String uuid) {
		return BaseResponse.onSuccess(SuccessStatus.FIND_BASE_ADDRESS_NAME,
			regionService.getBaseAddressName(uuid));
	}

	@Operation(summary = "활동 지역 수정", description = "1개의 활동 지역 정보를 수정한다.")
	@PutMapping("/{regionId}")
	public BaseResponse<Void> modifyRegion(
		@PathVariable Long regionId,
		@RequestBody RegionDetailDTO regionDetailDTO) {
		regionService.modifyRegion(regionId, regionDetailDTO);
		return BaseResponse.onSuccess(SuccessStatus.UPDATE_REGION, null);
	}

	@Operation(summary = "활동 지역 삭제", description = "1개의 활동 지역을 삭제한다.")
	@DeleteMapping("/{regionId}")
	public BaseResponse<Void> deleteRegion(
		@PathVariable Long regionId) {
		regionService.deleteRegion(regionId);
		return BaseResponse.onSuccess(SuccessStatus.DELETE_REGION, null);
	}

	@Operation(summary = "기본 활동 지역 변경",
		description = "기본 활동 지역(id)을 다른 활동 지역(id)으로 변경한다. newRegionId는 기존 것이 아니라 새로 설정하려는 id")
	@PostMapping("/base/{newRegionId}")
	public BaseResponse<Void> changeBaseRegion(
		@PathVariable Long newRegionId, @RequestHeader(name = "Uuid") String uuid) {
		regionService.changeBaseRegion(newRegionId, uuid);
		return BaseResponse.onSuccess(SuccessStatus.CHANGE_BASE_REGION, null);
	}

	@Operation(summary = "소모임 생성 시, 활동 지역 목록 조회", description = "회원이 소모임을 생성할때 필요한, 1~3개의 소모임 정보를 조회한다.")
	@GetMapping("/list")
	public BaseResponse<List<RegionXyDTO>> getRegionXY(@RequestHeader(name = "Uuid") String uuid) {
		return BaseResponse.onSuccess(SuccessStatus.FIND_REGION_XY,
			regionService.getRegionXY(uuid));
	}

}
