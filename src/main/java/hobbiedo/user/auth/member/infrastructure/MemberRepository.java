package hobbiedo.user.auth.member.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hobbiedo.user.auth.member.domain.IntegrateAuth;
import hobbiedo.user.auth.member.dto.response.LoginResponseDTO;

@Repository
public interface MemberRepository extends JpaRepository<IntegrateAuth, Long> {
	@Query("SELECT new hobbiedo.user.auth.member.dto.response.LoginResponseDTO(m.uuid, u.password) "
		   + "FROM IntegrateAuth u JOIN Member m WHERE u.loginId = :loginId")
	Optional<LoginResponseDTO> findByLoginId(@Param("loginId") String loginId);

	Boolean existsByMember_Email(String email);

	Boolean existsByMember_PhoneNumber(String phoneNumber);
}

