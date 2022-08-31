package pl.mazi.TransportServer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mazi.TransportServer.entity.Employee;
import pl.mazi.TransportServer.entity.EmployeeType;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    List<Employee> findAllByEmployeeType(EmployeeType employeeType);

}
