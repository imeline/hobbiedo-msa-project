package hobbiedo.user.auth.member.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hobbiedo.user.auth.member.domain.IntegrateAuth;
import hobbiedo.user.auth.member.dto.response.LoginResponseDTO;

@Repository
public interface MemberRepository extends JpaRepository<IntegrateAuth, Long> {
	Boolean existsByLoginId(String loginId);

	@Query(
		"SELECT new hobbiedo.user.auth.member.dto.response.LoginResponseDTO(u.member.uuid, u.password) "
			+ "FROM IntegrateAuth u WHERE u.loginId = :loginId")
	Optional<LoginResponseDTO> findByLoginId(@Param("loginId") String loginId);

	@Query("SELECT i.loginId FROM IntegrateAuth i JOIN i.member m WHERE m.name = :name AND m.email = :email")
	Optional<String> findLoginIdByNameAndEmail(@Param("name") String name,
		@Param("email") String email);

	@Query("SELECT COUNT(i) > 0 FROM IntegrateAuth i JOIN i.member m "
		+ "WHERE m.name = :name AND m.email = :email AND i.loginId = :loginId")
	Boolean existPasswordBy(@Param("name") String name, @Param("email") String email,
		@Param("loginId") String loginId);

	Boolean existsByMember_Email(String email);

	Boolean existsByMember_PhoneNumber(String phoneNumber);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE IntegrateAuth i SET i.password = :password WHERE i.loginId = :loginId")
	void updatePasswordByLoginId(@Param("password") String password,
		@Param("loginId") String loginId);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE IntegrateAuth i SET i.password = :password WHERE i.member.uuid = :uuid")
	void updatePasswordByUuid(@Param("password") String password, @Param("uuid") String uuid);

	@Query("SELECT i.password FROM IntegrateAuth i WHERE i.member.uuid = :uuid")
	String findPasswordByUuid(@Param("uuid") String uuid);
}

