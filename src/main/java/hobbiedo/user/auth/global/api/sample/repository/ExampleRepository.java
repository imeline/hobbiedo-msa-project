package hobbiedo.user.auth.global.api.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hobbiedo.user.auth.global.api.sample.domain.Example;

public interface ExampleRepository extends JpaRepository<Example, Long> {
}
