package hobbiedo.survey.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hobbiedo.survey.domain.UserHobby;

@Repository
public interface UserHobbyRepository extends JpaRepository<UserHobby, Long> {

	@Query("SELECT uh FROM UserHobby uh JOIN FETCH uh.hobby WHERE uh.uuid = :uuid")
	List<UserHobby> findByUuid(@Param("uuid") String uuid);
}
