package pl.mazi.TransportServer.entity;

import lombok.Data;
import pl.mazi.TransportServer.http.request.VehicleRequest;
import pl.mazi.TransportServer.http.response.VehicleResponse;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicles;

    @Column
    private String mark;

    @Column
    private String model;

    @Column
    private String mileage;

    @Column
    private String vinNumber;

    @Column
    private Double weight;

    @Column
    private String plateNumer;

    @Column
    private LocalDate dateOfTechnicalInspection;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idVehicleType")
    private VehicleType vehicleType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEmployee")
    private Employee employee;

    public void setNewVehicle(VehicleRequest vehicle){
        this.mark = vehicle.getMark();
        this.model = vehicle.getModel();
        this.mileage = vehicle.getMileage();
        this.vinNumber = vehicle.getVinNumber();
        this.weight = vehicle.getWeight();
        this.plateNumer = vehicle.getPlateNumber();
        this.dateOfTechnicalInspection = vehicle.getDateOfTechnicalInspection();
    }

    public void updateVehicle(VehicleResponse vehicleUpdate){
        if(vehicleUpdate.getMark() != null) this.mark = vehicleUpdate.getMark();
        if(vehicleUpdate.getModel() != null) this.model = vehicleUpdate.getModel();
        if(vehicleUpdate.getMileage() != null) this.mileage = vehicleUpdate.getMileage();
        if(vehicleUpdate.getVinNumber() != null) this.vinNumber = vehicleUpdate.getVinNumber();
        if(vehicleUpdate.getWeight() != null) this.weight = vehicleUpdate.getWeight();
        if(vehicleUpdate.getPlateNumber() != null) this.plateNumer = vehicleUpdate.getPlateNumber();
        if(vehicleUpdate.getDateOfTechnicalInspection() != null) this.dateOfTechnicalInspection = vehicleUpdate.getDateOfTechnicalInspection();
    }
}
