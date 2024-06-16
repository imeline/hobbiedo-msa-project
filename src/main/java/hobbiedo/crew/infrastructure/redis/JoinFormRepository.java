package hobbiedo.crew.infrastructure.redis;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hobbiedo.crew.domain.JoinForm;

public interface JoinFormRepository extends CrudRepository<JoinForm, String> {
	boolean existsByCrewIdAndUuid(Long crewId, String uuid);

	List<JoinForm> findByCrewId(Long crewId);
}

