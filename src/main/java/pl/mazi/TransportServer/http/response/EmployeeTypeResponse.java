package pl.mazi.TransportServer.http.response;

import lombok.Data;
import pl.mazi.TransportServer.entity.EmployeeType;

@Data
public class EmployeeTypeResponse {
    private Long idEmployeeType;
    private String type;


    public static EmployeeTypeResponse of(EmployeeType employeeType){
        EmployeeTypeResponse employeeTypeResponse = new EmployeeTypeResponse();
        employeeTypeResponse.setIdEmployeeType(employeeType.getIdEmployeeType());
        employeeTypeResponse.setType(employeeType.getType());
        return employeeTypeResponse;
    }
}
