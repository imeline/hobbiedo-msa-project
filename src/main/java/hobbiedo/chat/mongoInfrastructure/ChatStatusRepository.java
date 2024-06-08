package hobbiedo.chat.mongoInfrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import hobbiedo.chat.domain.ChatStatus;

public interface ChatStatusRepository extends MongoRepository<ChatStatus, String> {

	@Query(value = "{ 'uuid': ?0, 'crewId': ?1 }", fields = "{ 'lastReadAt': 1 }")
	Optional<ChatStatus> findByUuidAndCrewId(String uuid, Long crewId);

	@Query(value = "{ 'uuid': ?0 }", fields = "{ 'crewId': 1 }")
	List<ChatStatus> findByUuid(String uuid);

}
