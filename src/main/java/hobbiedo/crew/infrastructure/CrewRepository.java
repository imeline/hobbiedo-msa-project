package hobbiedo.crew.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import hobbiedo.crew.domain.Crew;

public interface CrewRepository extends JpaRepository<Crew, Long> {

}
