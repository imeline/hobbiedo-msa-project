package hobbiedo.user.auth.member.converter;

import java.util.UUID;

import hobbiedo.user.auth.member.domain.Member;
import hobbiedo.user.auth.member.dto.request.IntegrateSignUpDTO;
import hobbiedo.user.auth.member.vo.request.IntegrateSignUpVO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpConverter {
	public static IntegrateSignUpDTO toDTO(IntegrateSignUpVO signUpVO) {
		return IntegrateSignUpDTO.builder()
			.loginId(signUpVO.getLoginId())
			.name(signUpVO.getName())
			.email(signUpVO.getEmail())
			.phoneNumber(signUpVO.getPhoneNumber())
			.gender(signUpVO.getGender())
			.loginId(signUpVO.getLoginId())
			.password(signUpVO.getPassword())
			.birth(signUpVO.getBirth())
			.build();

	}

	public static Member toEntity(IntegrateSignUpDTO integrateSignUpDTO) {
		return Member.builder()
			.phoneNumber(integrateSignUpDTO.getPhoneNumber())
			.name(integrateSignUpDTO.getName())
			.uuid(UUID.randomUUID().toString())
			.email(integrateSignUpDTO.getEmail())
			.gender(integrateSignUpDTO.getGender())
			.birth(integrateSignUpDTO.getBirth())
			.build();
	}
}
