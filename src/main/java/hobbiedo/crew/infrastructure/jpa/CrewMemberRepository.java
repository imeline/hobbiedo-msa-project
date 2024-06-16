package hobbiedo.crew.infrastructure.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hobbiedo.crew.domain.CrewMember;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
	int countByUuidAndRole(String uuid, int role);

	boolean existsByCrewIdAndUuid(Long crewId, String uuid);

	boolean existsByCrewIdAndUuidAndBanned(Long crewId, String uuid, boolean banned);

	List<CrewMember> findByUuidAndBannedIsFalse(String uuid);

	Optional<CrewMember> findByCrewIdAndUuid(long crewId, String uuid);

	boolean existsByCrewIdAndUuidAndRole(Long crewId, String uuid, int role);

}
