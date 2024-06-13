package hobbiedo.crew.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import hobbiedo.crew.domain.HashTag;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
}
