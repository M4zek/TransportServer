package pl.mazi.TransportServer.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class VehicleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicleType;

    @Column
    private String type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicleType", orphanRemoval = true)
    private List<Vehicle> vehicle;
}
