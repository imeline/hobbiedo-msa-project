package hobbiedo.crew.infrastructure;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import hobbiedo.crew.domain.Chat;

public interface ChatRepository extends MongoRepository<Chat, String> {
	@Query("{ 'crewId': ?0, 'createdAt': { '$gt': ?1, '$lt': ?2 } }")
	List<Chat> findByCrewIdAndCreatedAtBetween(Long crewId, Instant start, Instant end);
}
