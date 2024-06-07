package hobbiedo.region.dto.response;

import hobbiedo.region.domain.Region;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegionAddressNameDTO {
	private Long regionId;
	private String addressName;

	public static RegionAddressNameDTO toDto(Region region) {
		return RegionAddressNameDTO.builder()
			.regionId(region.getId())
			.addressName(region.getAddressName())
			.build();
	}
}
