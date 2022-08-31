package pl.mazi.TransportServer.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class EmployeeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmployeeType;

    @Column
    private String type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeType", orphanRemoval = true)
    private List<Employee> employees;
}
