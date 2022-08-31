package pl.mazi.TransportServer.http.response;

import lombok.Data;
import pl.mazi.TransportServer.entity.Vehicle;

import java.time.LocalDate;

@Data
public class VehicleResponse {
    private Long idVehicle;
    private String driver;
    private String mark;
    private String model;
    private String vehicleType;
    private String mileage;
    private String vinNumber;
    private Double weight;
    private String plateNumber;
    private LocalDate dateOfTechnicalInspection;

    public static VehicleResponse of(Vehicle vehicle){
        VehicleResponse vehicleResponse = new VehicleResponse();
        vehicleResponse.setIdVehicle(vehicle.getIdVehicles());
        vehicleResponse.setMark(vehicle.getMark());
        vehicleResponse.setModel(vehicle.getModel());
        vehicleResponse.setMileage(vehicle.getMileage());
        vehicleResponse.setVinNumber(vehicle.getVinNumber());
        vehicleResponse.setWeight(vehicle.getWeight());
        vehicleResponse.setPlateNumber(vehicle.getPlateNumer());
        vehicleResponse.setDateOfTechnicalInspection(vehicle.getDateOfTechnicalInspection());
        if(vehicle.getEmployee() == null) vehicleResponse.setDriver("Brak");
        else vehicleResponse.setDriver(vehicle.getEmployee().getFirstName() + " " +vehicle.getEmployee().getLastName());
        if(vehicle.getVehicleType() == null) vehicleResponse.setVehicleType("");
        else vehicleResponse.setVehicleType(vehicle.getVehicleType().getType());

        return vehicleResponse;
    }
}
