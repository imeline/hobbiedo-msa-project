package hobbiedo.region.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hobbiedo.region.domain.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
	Optional<Region> findById(long regionId);

	List<Region> findByUuid(String uuid);

	Optional<Region> findByUuidAndIsBaseRegion(String uuid, boolean isBaseRegion);

	@Query("select r.addressName from Region r where r.id = :regionId")
	Optional<String> findAddressNameById(Long regionId);

	boolean existsById(long regionId);

	boolean existsByUuidAndLegalCode(String uuid, String legalCode);

	boolean existsByUuidAndIsBaseRegion(String uuid, boolean isBaseRegion);
}
