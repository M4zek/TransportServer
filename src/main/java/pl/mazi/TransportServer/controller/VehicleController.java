package pl.mazi.TransportServer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.mazi.TransportServer.entity.Employee;
import pl.mazi.TransportServer.entity.Vehicle;
import pl.mazi.TransportServer.entity.VehicleType;
import pl.mazi.TransportServer.http.request.EmployeeToDriverRequest;
import pl.mazi.TransportServer.http.request.VehicleRequest;
import pl.mazi.TransportServer.http.response.MessageResponse;
import pl.mazi.TransportServer.http.response.VehicleResponse;
import pl.mazi.TransportServer.http.response.VehicleTypeResponse;
import pl.mazi.TransportServer.repo.EmployeeRepo;
import pl.mazi.TransportServer.repo.VehicleRepo;
import pl.mazi.TransportServer.repo.VehicleTypeRepo;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    @Autowired
    private VehicleRepo vehicleRepo;

    @Autowired
    private VehicleTypeRepo vehicleTypeRepo;

    @Autowired
    private EmployeeRepo employeeRepo;


    /*
        ---- VEHICLES METHOD ----
     */
    @PostMapping("/updateVehicle")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<?> updateVehicle(@Valid @RequestBody VehicleResponse vehicleUpdate){
        if(!vehicleRepo.existsById(vehicleUpdate.getIdVehicle())){
            return ResponseEntity.ok().body("Nie ma takiego pojazdu!");
        }

        if(!vehicleTypeRepo.existsByType(vehicleUpdate.getVehicleType()) && vehicleUpdate.getVehicleType() != null){
            return ResponseEntity.ok().body(new MessageResponse("Nie ma takiego typu pojazdu!"));
        }

        Vehicle vehicle = vehicleRepo.findById(vehicleUpdate.getIdVehicle()).get();
        vehicle.updateVehicle(vehicleUpdate);

        VehicleType type = vehicleTypeRepo.findByType(vehicleUpdate.getVehicleType());
        if(type != null) vehicle.setVehicleType(type);

        vehicleRepo.save(vehicle);
        return ResponseEntity.ok().body(new MessageResponse("Pojazd zaaktualizowany pomyślnie!"));
    }

    @DeleteMapping("/deleteVehicle")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<?> deleteVehicle(@Valid @RequestBody Long id){
        if(!vehicleRepo.existsById(id)){
            return ResponseEntity.ok().body(new MessageResponse("Nie ma takiego pojazdu!"));
        }
        vehicleRepo.deleteById(id);
        return ResponseEntity.ok().body(new MessageResponse("Pojazd usunięty"));
    }

    @PostMapping("/addVehicle")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<?> addVehicle(@Valid @RequestBody VehicleRequest vehicleRequest){
        if(!vehicleTypeRepo.existsByType(vehicleRequest.getVehicleType())){
            return ResponseEntity.ok().body(new MessageResponse("Brak danych o takim typie pojazdu!"));
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setNewVehicle(vehicleRequest);
        VehicleType type = vehicleTypeRepo.findByType(vehicleRequest.getVehicleType());

        if(!(vehicleRequest.getIdEmployee() == null)) {
            if (employeeRepo.existsById(vehicleRequest.getIdEmployee())) {
                Optional<Employee> employeeOptional = employeeRepo.findById(vehicleRequest.getIdEmployee());
                vehicle.setEmployee(employeeOptional.get());
            }
        }

        vehicle.setVehicleType(type);
        vehicleRepo.save(vehicle);
        return ResponseEntity.ok().body(new MessageResponse("Pojazd dodany pomyślnie"));
    }

    @GetMapping("/getAllVehicle")
    @PreAuthorize("hasRole('OPERATOR')")
    public List<VehicleResponse> getAllVehicle(){
        return vehicleRepo
                .findAll()
                .stream()
                .map(VehicleResponse::of)
                .collect(Collectors.toList());
    }

    @PostMapping("/getVehicle")
    @PreAuthorize("hasRole('OPERATOR')")
    public VehicleResponse getVehicle(@Valid @RequestBody Long idVehicle){
        Optional<Vehicle> vehicle = vehicleRepo.findById(idVehicle);
        VehicleResponse vehicleResponse = VehicleResponse.of(vehicle.get());
        return vehicleResponse;
    }


    /*
            --- VEHICLES TYPE METHOD ----
     */

    @GetMapping("/getAllVehicleType")
    @PreAuthorize("hasRole('OPERATOR')")
    public List<VehicleTypeResponse> getAllVehicleType(){
        return vehicleTypeRepo
                .findAll()
                .stream()
                .map(VehicleTypeResponse::of)
                .collect(Collectors.toList());
    }


    @PostMapping("/addVehicleType")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<?> addVehicleType(@Valid @RequestBody String type){
        System.out.println("TYPE: " + type);
        type = type.substring(1,type.length()-1);
        if(vehicleTypeRepo.existsByType(type)){
            return ResponseEntity.ok().body(new MessageResponse("Taki typ już istnieje!"));
        }
        VehicleType vehicleType = new VehicleType();
        vehicleType.setType(type);
        vehicleTypeRepo.save(vehicleType);
        return ResponseEntity.ok().body(new MessageResponse("Typ pojazu dodany pomyślnie!"));
    }

    @DeleteMapping("/deleteVehicleType")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<?> deleteVehicleType(@Valid @RequestBody String type){
        type = type.substring(1,type.length()-1);
        if(!vehicleTypeRepo.existsByType(type)){
            return ResponseEntity.ok().body(new MessageResponse("Nie ma takiego typu pojazdu w bazie!"));
        }

        VehicleType vehicleType = vehicleTypeRepo.findByType(type);
        vehicleTypeRepo.deleteById(vehicleType.getIdVehicleType());
        return ResponseEntity.ok().body(new MessageResponse("Typ pojazdu: " + type + ",został usunięty"));
    }

    @PostMapping("/setEmployeeToVehicle")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<?> setEmployeeToVehicle(@Valid @RequestBody EmployeeToDriverRequest data){
        if(!(data.getIdEmployee() == -1)) {
            if (!employeeRepo.existsById(data.getIdEmployee())) {
                return ResponseEntity.ok().body(new MessageResponse("Nie ma takiego pracownika"));
            }
            if (!vehicleRepo.existsById(data.getIdVehicle())) {
                return ResponseEntity.ok().body(new MessageResponse("Nie ma takiego pojazdu"));
            }

            Optional<Vehicle> vehicleOptional = vehicleRepo.findById(data.getIdVehicle());
            Vehicle vehicle = vehicleOptional.get();

            Optional<Employee> optionalEmployee = employeeRepo.findById(data.getIdEmployee());
            Employee employee = optionalEmployee.get();

            vehicle.setEmployee(employee);
            vehicleRepo.save(vehicle);
            return ResponseEntity.ok().body(new MessageResponse("Pracownik: " + employee.getFirstName() + " " + employee.getLastName() + " został przypisany do: " + vehicle.getModel() + " " + vehicle.getMark()));
        }else{
            if (!vehicleRepo.existsById(data.getIdVehicle())) {
                return ResponseEntity.ok().body(new MessageResponse("Nie ma takiego pojazdu"));
            }
            Optional<Vehicle> vehicleOptional = vehicleRepo.findById(data.getIdVehicle());
            Vehicle vehicle = vehicleOptional.get();
            vehicle.setEmployee(null);
            vehicleRepo.save(vehicle);
            return ResponseEntity.ok().body(new MessageResponse("Zwolniłeś pracownika z pojazdu: " + vehicle.getModel() +" "+ vehicle.getMark()));
        }
    }
}
