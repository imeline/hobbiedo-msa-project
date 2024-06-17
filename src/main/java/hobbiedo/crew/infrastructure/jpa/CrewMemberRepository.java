package hobbiedo.crew.infrastructure.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hobbiedo.crew.domain.CrewMember;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
	int countByUuidAndRole(String uuid, int role);

	boolean existsByCrewIdAndUuid(Long crewId, String uuid);

	boolean existsByCrewIdAndUuidAndRole(Long crewId, String uuid, int role);

	@Query("SELECT cm FROM CrewMember cm WHERE cm.uuid = :uuid AND cm.role IN (0, 1)")
	List<CrewMember> findByUuidAndRole(String uuid);

	Optional<CrewMember> findByCrewIdAndUuid(long crewId, String uuid);

	Optional<CrewMember> findByCrewIdAndRole(long crewId, int role);

}
