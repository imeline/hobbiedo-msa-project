package hobbiedo.user.auth.member.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hobbiedo.user.auth.member.domain.Member;

@Repository
public interface MemberProfileRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByUuid(String uuid);
}
