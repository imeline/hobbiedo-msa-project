package hobbiedo.crew.infrastructure.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hobbiedo.crew.domain.Crew;

public interface CrewRepository extends JpaRepository<Crew, Long> {

	List<Crew> findAllByHobbyId(long hobbyId);

	List<Crew> findTop5ByOrderByCreatedAtDesc();
}
