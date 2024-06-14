package hobbiedo.user.auth.google.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hobbiedo.user.auth.member.domain.Member;

public interface GoogleMemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);
}
