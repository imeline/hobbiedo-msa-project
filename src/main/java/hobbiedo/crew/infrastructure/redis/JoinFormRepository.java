package hobbiedo.crew.infrastructure.redis;

import org.springframework.data.repository.CrudRepository;

import hobbiedo.crew.domain.JoinForm;

public interface JoinFormRepository extends CrudRepository<JoinForm, Long> {
	boolean existsByCrewIdAndUuid(Long crewId, String uuid);
}
