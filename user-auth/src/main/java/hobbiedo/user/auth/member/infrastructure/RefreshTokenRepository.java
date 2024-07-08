package hobbiedo.user.auth.member.infrastructure;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import hobbiedo.user.auth.member.domain.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
	Optional<RefreshToken> findByUuid(String uuid);

	void deleteByRefresh(String refresh);

	Boolean existsByRefresh(String refresh);
}
