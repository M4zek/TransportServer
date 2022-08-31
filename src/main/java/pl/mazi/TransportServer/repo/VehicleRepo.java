package pl.mazi.TransportServer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mazi.TransportServer.entity.Vehicle;


public interface VehicleRepo extends JpaRepository<Vehicle, Long> {

}
