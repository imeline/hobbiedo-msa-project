package hobbiedo.region.dto.response;

import hobbiedo.region.domain.Region;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegionXyDTO {
	private Long regionId;
	private String addressName;
	private double latitude;
	private double longitude;

	public static RegionXyDTO toDto(Region region) {
		return RegionXyDTO.builder()
			.regionId(region.getId())
			.addressName(region.getAddressName())
			.latitude(region.getLatitude())
			.longitude(region.getLongitude())
			.build();
	}
}
