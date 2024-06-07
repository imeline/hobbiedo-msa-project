package hobbiedo.region.application;

import java.util.List;

import hobbiedo.region.dto.request.RegionDetailDTO;
import hobbiedo.region.dto.request.RegionSingUpDTO;
import hobbiedo.region.dto.response.RegionAddressNameDTO;
import hobbiedo.region.dto.response.RegionGetDetailDTO;
import hobbiedo.region.dto.response.RegionXyDTO;

public interface RegionService {
	void addRegion(RegionDetailDTO regionDetailDto, String uuid);

	List<RegionAddressNameDTO> getAddressNames(String uuid);

	RegionGetDetailDTO getRegionDetail(Long regionId);

	RegionAddressNameDTO getBaseAddressName(String uuid);

	void modifyRegion(Long regionId, RegionDetailDTO regionDetailDto);

	void deleteRegion(Long regionId);

	void changeBaseRegion(Long newRegionId, String uuid);

	List<RegionXyDTO> getRegionXY(String uuid);

	void singUpRegion(RegionSingUpDTO regionSingUpDTO);
}
