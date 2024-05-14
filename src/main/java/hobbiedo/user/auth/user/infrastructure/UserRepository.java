package hobbiedo.user.auth.user.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hobbiedo.user.auth.user.domain.User;
import hobbiedo.user.auth.user.dto.request.LoginRequestDTO;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<LoginRequestDTO> findUserByUsername(String username);
}
