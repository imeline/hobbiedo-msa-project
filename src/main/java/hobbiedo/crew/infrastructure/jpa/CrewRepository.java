package hobbiedo.crew.infrastructure.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hobbiedo.crew.domain.Crew;

public interface CrewRepository extends JpaRepository<Crew, Long> {

	@Query("select c.id from Crew c where c.hobbyId = :hobbyId and c.regionId = :regionId and c.active = true")
	List<Long> findIdsByHobbyAndRegion(long hobbyId, long regionId);
}
