package hobbiedo.survey.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hobbiedo.survey.domain.UserHobby;

@Repository
public interface UserHobbyRepository extends JpaRepository<UserHobby, Long> {

	// 사용자의 취미 정보를 가져오는 쿼리
	@Query("SELECT uh FROM UserHobby uh JOIN FETCH uh.hobby WHERE uh.uuid = :uuid")
	List<UserHobby> findByUuid(@Param("uuid") String uuid);

	// 해당 uuid 의 취미 정보를 모두 삭제하는 쿼리
	@Modifying
	@Query("DELETE FROM UserHobby uh WHERE uh.uuid = :uuid")
	void deleteByUuid(@Param("uuid") String uuid);

	List<UserHobby> findByUuidOrderByFitRateDesc(String uuid);
}
