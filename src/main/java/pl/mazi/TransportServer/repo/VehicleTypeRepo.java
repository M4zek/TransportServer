package pl.mazi.TransportServer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mazi.TransportServer.entity.VehicleType;

public interface VehicleTypeRepo extends JpaRepository<VehicleType, Long> {
    Boolean existsByType(String type);

    VehicleType findByType(String type);
}
