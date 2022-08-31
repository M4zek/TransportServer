package pl.mazi.TransportServer.http.response;

import lombok.Data;
import pl.mazi.TransportServer.entity.Employee;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class EmployeeResponse {
    private Long idEmployee;
    private String firstName;
    private String lastName;
    private LocalDate hireDate;
    private LocalDate birthDate;
    private String peselNumber;
    private String placeOfResidence;
    private Double salary;
    private String employeeType;
    private String phoneNumber;

    public static EmployeeResponse of(Employee employee){
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setIdEmployee(employee.getIdEmployee());
        employeeResponse.setFirstName(employee.getFirstName());
        employeeResponse.setLastName(employee.getLastName());
        employeeResponse.setHireDate(employee.getHireDate());
        employeeResponse.setBirthDate(employee.getBirthDate());
        employeeResponse.setSalary(employee.getSalary());
        employeeResponse.setPeselNumber(employee.getPeselNumber());
        employeeResponse.setPlaceOfResidence(employee.getPlaceOfResidence());
        employeeResponse.setEmployeeType(employee.getEmployeeType().getType());
        employeeResponse.setPhoneNumber(employee.getPhoneNumber());
        return employeeResponse;
    }

    public static EmployeeResponse of(Optional<Employee> employee){
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setIdEmployee(employee.get().getIdEmployee());
        employeeResponse.setFirstName(employee.get().getFirstName());
        employeeResponse.setLastName(employee.get().getLastName());
        employeeResponse.setHireDate(employee.get().getHireDate());
        employeeResponse.setBirthDate(employee.get().getBirthDate());
        employeeResponse.setSalary(employee.get().getSalary());
        employeeResponse.setPeselNumber(employee.get().getPeselNumber());
        employeeResponse.setPlaceOfResidence(employee.get().getPlaceOfResidence());
        employeeResponse.setEmployeeType(employee.get().getEmployeeType().getType());
        employeeResponse.setPhoneNumber(employee.get().getPhoneNumber());
        return employeeResponse;
    }
}