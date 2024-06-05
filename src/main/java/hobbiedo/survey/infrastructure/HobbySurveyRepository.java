package hobbiedo.survey.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hobbiedo.survey.domain.HobbySurvey;

@Repository
public interface HobbySurveyRepository extends JpaRepository<HobbySurvey, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM hobby_survey ORDER BY RAND() LIMIT 20")
	List<HobbySurvey> findRandomQuestions();
}
