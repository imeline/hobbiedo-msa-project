package hobbiedo.user.auth.user.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hobbiedo.user.auth.user.domain.Member;
import hobbiedo.user.auth.user.dto.response.LoginResponseDTO;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	@Query("SELECT new hobbiedo.user.auth.user.dto.response.LoginResponseDTO(u.uuid,u.password) "
			+ "FROM Member u WHERE u.loginId = :loginId")
	Optional<LoginResponseDTO> findByLoginId(@Param("loginId") String loginId);
}

