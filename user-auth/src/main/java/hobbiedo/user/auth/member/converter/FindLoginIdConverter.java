package hobbiedo.user.auth.member.converter;

import hobbiedo.user.auth.member.dto.request.FindLoginIdDTO;
import hobbiedo.user.auth.member.vo.request.FindLoginIdVO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FindLoginIdConverter {
	public static FindLoginIdDTO toDTO(FindLoginIdVO findLoginIdVO) {
		return FindLoginIdDTO.builder()
			.name(findLoginIdVO.getName())
			.email(findLoginIdVO.getEmail())
			.build();
	}
}
