package hobbiedo.survey.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hobbiedo.survey.domain.Hobby;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Long> {
}
