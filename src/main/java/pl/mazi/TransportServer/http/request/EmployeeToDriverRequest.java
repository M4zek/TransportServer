package pl.mazi.TransportServer.http.request;

import lombok.Data;

@Data
public class EmployeeToDriverRequest {
    private Long idVehicle;
    private Long idEmployee;
}
