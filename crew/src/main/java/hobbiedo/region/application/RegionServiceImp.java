package hobbiedo.region.application;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.global.exception.GlobalException;
import hobbiedo.global.status.ErrorStatus;
import hobbiedo.region.domain.Region;
import hobbiedo.region.dto.request.RegionDetailDTO;
import hobbiedo.region.dto.response.RegionAddressNameDTO;
import hobbiedo.region.dto.response.RegionGetDetailDTO;
import hobbiedo.region.dto.response.RegionXyDTO;
import hobbiedo.region.infrastructure.RegionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionServiceImp implements RegionService {

	private final RegionRepository regionRepository;

	@Override
	@Transactional
	public void addRegion(RegionDetailDTO regionDetailDTO, String uuid) {
		isValidLegalCode(uuid, regionDetailDTO.getLegalCode());
		isValidRange(regionDetailDTO.getCurrentSelectedRange());
		regionRepository.save(regionDetailDTO.toAddRegion(uuid));
	}

	@Override
	public List<RegionAddressNameDTO> getAddressNames(String uuid) {
		return Optional.of(regionRepository.findByUuid(uuid))
			.filter(regionList -> !regionList.isEmpty())
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_REGION))
			.stream()
			.map(RegionAddressNameDTO::toDto)
			.toList();
	}

	@Override
	public RegionGetDetailDTO getRegionDetail(Long regionId) {
		return RegionGetDetailDTO.toDto(getRegionById(regionId));
	}

	@Override
	public RegionAddressNameDTO getBaseAddressName(String uuid) {
		Region region = regionRepository.findByUuidAndIsBaseRegion(uuid,
				true)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_BASE_REGION));
		return RegionAddressNameDTO.toDto(region);
	}

	@Override
	@Transactional
	public void modifyRegion(Long regionId, RegionDetailDTO regionDetailDTO, String uuid) {
		Region nowRegion = getRegionById(regionId);
		if (!nowRegion.getLegalCode().equals(regionDetailDTO.getLegalCode())) {
			isValidLegalCode(uuid, regionDetailDTO.getLegalCode());
		}
		isValidRange(regionDetailDTO.getCurrentSelectedRange());
		regionRepository.save(
			regionDetailDTO.toModifyRegion(nowRegion));
	}

	@Override
	@Transactional
	public void deleteRegion(Long regionId) {
		regionRepository.deleteById(regionId);
	}

	@Override
	@Transactional
	public void changeBaseRegion(Long newRegionId, String uuid) {
		// 기존 활성화된 활동 지역 찾기
		Region nowRegion = regionRepository.findByUuidAndIsBaseRegion(uuid,
				true)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_BASE_REGION));
		// 기존 활성화된 활동 지역 비활성화
		regionRepository.save(changeIsBaseRegion(nowRegion, false));
		// 새로운 활동 지역 활성화
		regionRepository.save(changeIsBaseRegion(getRegionById(newRegionId), true));
	}

	private Region changeIsBaseRegion(Region region, boolean isBaseRegion) {
		return Region.builder()
			.id(region.getId())
			.uuid(region.getUuid())
			.currentSelectedRange(region.getCurrentSelectedRange())
			.latitude(region.getLatitude())
			.longitude(region.getLongitude())
			.addressName(region.getAddressName())
			.legalCode(region.getLegalCode())
			.isBaseRegion(isBaseRegion)
			.build();
	}

	@Override
	public List<RegionXyDTO> getRegionXY(String uuid) {
		return Optional.of(regionRepository.findByUuid(uuid))
			.filter(regionList -> !regionList.isEmpty())
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_REGION))
			.stream()
			.map(RegionXyDTO::toDto)
			.toList();
	}

	@Override
	@Transactional
	public void addBaseRegion(RegionDetailDTO regionDetailDTO, String uuid) {
		if (regionRepository.existsByUuidAndIsBaseRegion(uuid, true)) {
			throw new GlobalException(ErrorStatus.EXIST_BASE_REGION);
		}
		isValidLegalCode(uuid, regionDetailDTO.getLegalCode());
		isValidRange(regionDetailDTO.getCurrentSelectedRange());
		regionRepository.save(regionDetailDTO.toBaseEntity(uuid));
	}

	@Override
	public Region getRegionById(Long regionId) {
		return regionRepository.findById(regionId)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_REGION));
	}

	@Override
	public String getAddressNameById(long regionId) {
		return regionRepository.findAddressNameById(regionId)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_REGION));
	}

	private void isValidRange(int currentSelectedRange) {
		List<Integer> validRanges = Arrays.asList(3, 5, 7, 10);
		if (!validRanges.contains(currentSelectedRange)) {
			throw new GlobalException(ErrorStatus.INVALID_RANGE);
		}

	}

	private void isValidLegalCode(String uuid, String legalCode) {
		if (regionRepository.existsByUuidAndLegalCode(uuid, legalCode)) {
			throw new GlobalException(ErrorStatus.EXIST_REGION);
		}
	}
}
