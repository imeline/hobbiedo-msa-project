package hobbiedo.region.dto.request;

import hobbiedo.region.domain.Region;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegionSingUpDTO {
	private String uuid;
	private String addressName;
	private String legalCode;
	private double latitude;
	private double longitude;
	private int currentSelectedRange;

	public Region toEntity() {
		return Region.builder()
			.uuid(uuid)
			.currentSelectedRange(currentSelectedRange)
			.latitude(latitude)
			.longitude(longitude)
			.addressName(addressName)
			.legalCode(legalCode)
			.isBaseRegion(true) // 회원가입 시라 무조건 base 로 설정
			.build();
	}
}
