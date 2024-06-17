package hobbiedo.crew.infrastructure.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hobbiedo.crew.domain.Crew;

public interface CrewRepository extends JpaRepository<Crew, Long> {

	// @Query("select c.id from Crew c where c.hobbyId = :hobbyId and c.regionId = :regionId and c.active = true")
	// List<Long> findIdsByHobbyAndRegion(long hobbyId, long regionId);

	List<Crew> findAllByHobbyId(long hobbyId);
}
