package hobbiedo.user.auth.google.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import hobbiedo.user.auth.google.domain.SocialAuth;
import hobbiedo.user.auth.member.domain.Member;

public interface SocialAuthRepository extends JpaRepository<SocialAuth, Long> {
	Boolean existsByMember(Member member);
}
