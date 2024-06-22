package hobbiedo.crew.infrastructure;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import hobbiedo.crew.domain.Crew;

public interface ReplicaCrewRepository extends MongoRepository<Crew, String> {
	Optional<Crew> findByCrewId(long crewId);
}
