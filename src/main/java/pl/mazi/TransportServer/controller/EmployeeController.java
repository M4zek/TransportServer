package pl.mazi.TransportServer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.mazi.TransportServer.entity.*;
import pl.mazi.TransportServer.http.request.SignupRequest;
import pl.mazi.TransportServer.http.response.DriverResponse;
import pl.mazi.TransportServer.http.response.EmployeeResponse;
import pl.mazi.TransportServer.http.response.EmployeeTypeResponse;
import pl.mazi.TransportServer.http.response.MessageResponse;
import pl.mazi.TransportServer.repo.EmployeeRepo;
import pl.mazi.TransportServer.repo.EmployeeTypeRepo;
import pl.mazi.TransportServer.repo.RoleRepository;
import pl.mazi.TransportServer.repo.UserRepository;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin/employee")
@RequiredArgsConstructor
public class EmployeeController {
    
    @Autowired
    private final EmployeeRepo employeeRepo;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmployeeTypeRepo employeeTypeRepo;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/all")
    @PreAuthorize("hasRole('OPERATOR')")
    public List<EmployeeResponse> getAllEmployee(){
        return employeeRepo
                .findAll()
                .stream()
                .map(EmployeeResponse::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/getAllEmployeeType")
    @PreAuthorize("hasRole('OPERATOR')")
    public List<EmployeeTypeResponse> getAllEmployeeType(){
        return employeeTypeRepo
                .findAll()
                .stream()
                .map(EmployeeTypeResponse::of)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/deleteEmployeeType")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<?> deleteEmployeeType(@Valid @RequestBody String type){
        type = type.substring(1,type.length() - 1);
        if(!employeeTypeRepo.existsByType(type)){
            return ResponseEntity.ok().body(new MessageResponse("Nie ma takiego stanowiska"));
        }
        EmployeeType employeeType = employeeTypeRepo.findByType(type);
        employeeTypeRepo.deleteById(employeeType.getIdEmployeeType());
        return ResponseEntity.ok().body(new MessageResponse("Stanowisko" + type +", zostało usunięte!"));
    }

    @PostMapping("/addEmployeeType")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<?> addEmployeeType(@Valid @RequestBody String type){
        type = type.substring(1,type.length() - 1);
        if(employeeTypeRepo.existsByType(type)) {
            return ResponseEntity.ok().body(new MessageResponse("Takie stanowisko już istnieje"));
        }
        EmployeeType newType = new EmployeeType();
        newType.setType(type);
        employeeTypeRepo.save(newType);
        return ResponseEntity.ok().body(new MessageResponse("Stanowisko: " + type + ", zostało dodane."));
    }


    @PostMapping("/getEmployee")
    @PreAuthorize("hasRole('OPERATOR')")
    public EmployeeResponse getEmployee(@RequestBody Long idEmployee){
        Optional<Employee> employee = employeeRepo.findById(idEmployee);
        return EmployeeResponse.of(employee);
    }

    @GetMapping("/getAllDrivers")
    @PreAuthorize("hasRole('OPERATOR')")
    public List<DriverResponse> getAllDriver(){
        EmployeeType employeeType = employeeTypeRepo.findByType("Kierowca");
        return employeeRepo.findAllByEmployeeType(employeeType)
                .stream()
                .map(DriverResponse::of)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<?> deleteEmploye(@Valid @RequestBody Long idEmployee){
        employeeRepo.deleteById(idEmployee);
        return ResponseEntity.ok().body(new MessageResponse("Pracownik usunięty"));
    }

    @PostMapping("/updateEmployee")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeResponse employeeResponse){
        Optional<Employee> employeeOptional = employeeRepo.findById(employeeResponse.getIdEmployee());
        EmployeeType employeeType = employeeTypeRepo.findByType(employeeResponse.getEmployeeType());
        Employee employee = employeeOptional.get();
        employee.updateEmployee(employeeResponse,employeeType);
        employeeRepo.save(employee);
        return ResponseEntity.ok().body(new MessageResponse("Pracownik został zmodyfikowany!"));
    }


    @PostMapping("/addNewEmployeeOperator")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<?> addNewEmployeeOperatorSystem(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.ok(new MessageResponse("Email zajęty!"));
        }

        if(userRepository.existsByUsername(signUpRequest.getUsername())){
            return ResponseEntity.ok(new MessageResponse("Nazwa użytkownika zajęta!"));
        }

        if(!employeeTypeRepo.existsByType(signUpRequest.getEmployeType())){
            return ResponseEntity.ok(new MessageResponse("Nie znaleziono stanowiska!"));
        }

        User user = new User(signUpRequest.getEmail(), signUpRequest.getUsername(),encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null){
            Role driverRole = roleRepository.findByName(ERole.ROLE_DRIVER).orElseThrow(()-> new RuntimeException("Nie znaleziono tytułu."));
            roles.add(driverRole);
        } else {
            strRoles.forEach(role ->{
                if ("Operator".equals(role)) {
                    Role operatorRole = roleRepository.findByName(ERole.ROLE_OPERATOR).orElseThrow(() -> new RuntimeException("Nie znaleziono tytułu."));
                    roles.add(operatorRole);
                } else if ("Kierowca".equals(role)){
                    Role driverRole = roleRepository.findByName(ERole.ROLE_DRIVER).orElseThrow(() -> new RuntimeException("Nie znaleziono tytułu."));
                    roles.add(driverRole);
                }
            });
        }
        user.setRoles(roles);

        Employee employee = new Employee();
        EmployeeType employeeType = employeeTypeRepo.findByType(signUpRequest.getEmployeType());
        employee.setEmployeeType(employeeType);
        employee.setFirstName(signUpRequest.getFirstName());
        employee.setLastName(signUpRequest.getLastName());
        employee.setUser(user);
        employee.setHireDate(signUpRequest.getHireDate());
        employee.setBirthDate(signUpRequest.getBirthDate());
        employee.setSalary(signUpRequest.getSalary());
        employee.setPeselNumber(signUpRequest.getPeselNumber());
        employee.setPlaceOfResidence(signUpRequest.getPlaceOfResidence());
        employee.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setEmployee(employee);
        userRepository.save(user);
        employeeRepo.save(employee);

        // TODO dorobić wysyłanie na maila danych z rejestracji oraz zastanowić sie czy to wszystkie kolumny w bazie

        return ResponseEntity.ok(new MessageResponse("Użytkownik systemu dodany pomyślnie!"));
    }


    @PostMapping("/addNewEmployee")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<?> addNewEmployee(@Valid @RequestBody SignupRequest signUpRequest){

        System.out.println(signUpRequest.toString());

        if(!employeeTypeRepo.existsByType(signUpRequest.getEmployeType())){
            return ResponseEntity.ok(new MessageResponse("Nie znaleziono stanowiska!"));
        }

        Employee employee = new Employee();
        EmployeeType employeeType = employeeTypeRepo.findByType(signUpRequest.getEmployeType());
        employee.setEmployeeType(employeeType);
        employee.setFirstName(signUpRequest.getFirstName());
        employee.setLastName(signUpRequest.getLastName());
        employee.setHireDate(signUpRequest.getHireDate());
        employee.setBirthDate(signUpRequest.getBirthDate());
        employee.setSalary(signUpRequest.getSalary());
        employee.setPeselNumber(signUpRequest.getPeselNumber());
        employee.setPlaceOfResidence(signUpRequest.getPlaceOfResidence());
        employee.setPhoneNumber(signUpRequest.getPhoneNumber());
        employeeRepo.save(employee);
        return ResponseEntity.ok(new MessageResponse("Pracownik dodany pomyślnie!"));
    }
}