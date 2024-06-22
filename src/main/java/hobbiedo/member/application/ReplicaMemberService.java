package hobbiedo.member.application;

import hobbiedo.member.kafka.dto.ModifyProfileDTO;
import hobbiedo.member.kafka.dto.SignUpDTO;

public interface ReplicaMemberService {
	void createMemberProfile(SignUpDTO signUpDTO);

	void updateMemberProfile(ModifyProfileDTO modifyProfileDTO);

	String getMemberName(String writerUuid);

	String getMemberProfileImageUrl(String writerUuid);
}
