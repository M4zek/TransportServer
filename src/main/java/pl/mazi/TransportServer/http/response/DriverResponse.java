package pl.mazi.TransportServer.http.response;

import lombok.Data;
import pl.mazi.TransportServer.entity.Employee;

@Data
public class DriverResponse {
    private Long idEmployee;
    private String firstName;
    private String lastName;

    public DriverResponse(){}
    public DriverResponse(Long idEmployee, String firstName, String lastName) {
        this.idEmployee = idEmployee;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static DriverResponse of(Employee employee){
        return new DriverResponse(
                employee.getIdEmployee(),
                employee.getFirstName(),
                employee.getLastName()
        );
    }
}
