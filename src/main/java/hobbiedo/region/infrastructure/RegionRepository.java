package hobbiedo.region.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hobbiedo.region.domain.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
	List<Region> findByUuid(String uuid);

	Optional<Region> findByUuidAndBaseRegion(String uuid, boolean baseRegion);

	@Query("select r.addressName from Region r where r.id = :regionId")
	Optional<String> findAddressNameById(Long regionId);

	boolean existsById(long regionId);

}
