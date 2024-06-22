package hobbiedo.member.infrastructure;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import hobbiedo.member.domain.MemberProfile;

public interface ReplicaMemberRepository extends MongoRepository<MemberProfile, String> {
	Optional<MemberProfile> findByUuid(String uuid);
}
