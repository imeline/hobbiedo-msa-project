package hobbiedo.region.dto.request;

import java.util.Arrays;
import java.util.List;

import hobbiedo.region.domain.Region;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegionDetailDTO {
	private String addressName;
	private String legalCode;
	private double latitude;
	private double longitude;
	private int currentSelectedRange;

	public Region toCreateRegion(String uuid) {
		return Region.builder()
			.uuid(uuid)
			.currentSelectedRange(currentSelectedRange)
			.latitude(latitude)
			.longitude(longitude)
			.addressName(addressName)
			.legalCode(legalCode)
			.isBaseRegion(false)
			.build();
	}

	public Region toModifyRegion(Region region) {
		return Region.builder()
			.id(region.getId())
			.uuid(region.getUuid())
			.currentSelectedRange(currentSelectedRange)
			.latitude(latitude)
			.longitude(longitude)
			.addressName(addressName)
			.legalCode(legalCode)
			.isBaseRegion(region.isBaseRegion())
			.build();
	}

	public boolean isInvalidRange() {
		List<Integer> validRanges = Arrays.asList(3, 5, 7, 10);
		return !validRanges.contains(currentSelectedRange);
	}
}
