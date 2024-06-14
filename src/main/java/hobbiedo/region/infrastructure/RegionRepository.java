package hobbiedo.region.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hobbiedo.region.domain.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
	List<Region> findByUuid(String uuid);

	Optional<Region> findByUuidAndBaseRegion(String uuid, boolean baseRegion);

}
