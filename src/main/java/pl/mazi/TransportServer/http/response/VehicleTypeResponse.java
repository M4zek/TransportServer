package pl.mazi.TransportServer.http.response;

import lombok.Data;
import pl.mazi.TransportServer.entity.VehicleType;

@Data
public class VehicleTypeResponse {
    private Long idVehicleType;
    private String type;

    public VehicleTypeResponse(Long idVehicleType, String type) {
        this.idVehicleType = idVehicleType;
        this.type = type;
    }

    public static VehicleTypeResponse of(VehicleType vehicleType){
        return new VehicleTypeResponse(
                vehicleType.getIdVehicleType(),
                vehicleType.getType()
        );
    }
}
