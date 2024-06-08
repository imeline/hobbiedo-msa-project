package hobbiedo.chat.mongoInfrastructure;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import hobbiedo.chat.domain.UnReadCount;

public interface UnReadCountRepository extends MongoRepository<UnReadCount, String> {
	@Query(value = "{ 'crewId': ?0, 'uuid': ?1 }", fields = "{ 'unreadCount': 1 }")
	Optional<UnReadCount> findByCrewIdAndUuid(Long crewId, String uuid);
}

