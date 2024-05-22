package hobbiedo.user.auth.user.infrastructure;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import hobbiedo.user.auth.user.domain.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
	Optional<RefreshToken> findById(String id);

	void deleteByRefresh(String refresh);

	Boolean existsByRefresh(String refresh);
}
