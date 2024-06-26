package hobbiedo.crew.infrastructure.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hobbiedo.crew.domain.Crew;

public interface CrewRepository extends JpaRepository<Crew, Long> {

	List<Crew> findAllByHobbyId(long hobbyId);

	@Query("SELECT c FROM Crew c WHERE c.score != 0")
	List<Crew> findCrewsByScoreNotZero();
}
