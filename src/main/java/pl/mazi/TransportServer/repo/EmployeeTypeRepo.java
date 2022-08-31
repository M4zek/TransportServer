package pl.mazi.TransportServer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mazi.TransportServer.entity.EmployeeType;

import java.util.Optional;

public interface EmployeeTypeRepo extends JpaRepository<EmployeeType,Long> {
    EmployeeType findByType(String type);

    Boolean existsByType(String type);

    Optional<EmployeeType> findTopByOrderByIdEmployeeTypeAsc();

    Long removeByType(String type);
}
