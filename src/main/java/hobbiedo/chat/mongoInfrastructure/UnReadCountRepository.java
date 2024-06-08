package hobbiedo.chat.mongoInfrastructure;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import hobbiedo.chat.domain.UnReadCount;

public interface UnReadCountRepository extends MongoRepository<UnReadCount, String> {
	Optional<UnReadCount> findByCrewIdAndUuid(Long crewId, String uuid);
}

