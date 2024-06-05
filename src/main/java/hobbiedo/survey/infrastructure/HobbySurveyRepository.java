package hobbiedo.survey.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hobbiedo.survey.domain.HobbySurvey;

@Repository
public interface HobbySurveyRepository extends JpaRepository<HobbySurvey, Long> {
}
