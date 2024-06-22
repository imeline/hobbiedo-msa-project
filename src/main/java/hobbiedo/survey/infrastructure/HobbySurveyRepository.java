package hobbiedo.survey.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hobbiedo.survey.domain.HobbySurvey;

@Repository
public interface HobbySurveyRepository extends JpaRepository<HobbySurvey, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM hobby_survey ORDER BY RAND() LIMIT 20")
	List<HobbySurvey> findRandomQuestions();

	// 취미 설문 문항을 QuestionType 별로 5개씩 랜덤으로 가져오는 쿼리
	@Query(nativeQuery = true, value = "SELECT * FROM hobby_survey WHERE question_type = :questionType ORDER BY RAND() LIMIT 5")
	List<HobbySurvey> findRandomQuestionsByType(@Param("questionType") String questionType);
}
