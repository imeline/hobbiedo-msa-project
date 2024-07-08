package hobbiedo.region.dto.response;

import hobbiedo.region.domain.Region;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegionGetDetailDTO {
	private String addressName;
	private double latitude;
	private double longitude;
	private int currentSelectedRange;

	public static RegionGetDetailDTO toDto(Region region) {
		return RegionGetDetailDTO.builder()
			.addressName(region.getAddressName())
			.latitude(region.getLatitude())
			.longitude(region.getLongitude())
			.currentSelectedRange(region.getCurrentSelectedRange())
			.build();
	}
}
