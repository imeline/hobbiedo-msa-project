package hobbiedo.survey.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hobbiedo.survey.domain.Hobby;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM hobby ORDER BY RAND() LIMIT 10")
	List<Hobby> findRandomHobbies();
}
