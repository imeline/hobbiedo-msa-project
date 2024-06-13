package hobbiedo.crew.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import hobbiedo.crew.domain.CrewMember;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
	int countByUuidAndRole(String uuid, int role);
}
