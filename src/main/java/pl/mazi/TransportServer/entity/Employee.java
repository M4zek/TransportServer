package pl.mazi.TransportServer.entity;

import lombok.*;
import pl.mazi.TransportServer.http.response.EmployeeResponse;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmployee; // ID pracownika

    @Column // Imie pracownika
    private String firstName;

    @Column // Nazwisko pracownika
    private String lastName;

    @Column // Data urodzenia pracownika
    private LocalDate birthDate;

    @Column // Data zatrudnienia pracownika
    private LocalDate hireDate;

    @Column // Wypłata pracownika
    private Double salary;

    @Column // Numer pesel pracownika
    private String peselNumber;

    @Column
    private String phoneNumber;

    @Column
    private String placeOfResidence;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEmployeeType")
    private EmployeeType employeeType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", orphanRemoval = true)
    private List<Vehicle> vehicleList;

    @OneToOne(mappedBy = "employee", orphanRemoval = true)
    private User user;

    /*
    METHODS
     */

    public void update(String firstName, String lastName, String phoneNumber, String placeOfResidence){
        if(!firstName.trim().isEmpty()) this.firstName = firstName;
        if(!lastName.trim().isEmpty()) this.lastName = lastName;
        if(!phoneNumber.trim().isEmpty()) this.phoneNumber = phoneNumber;
        if(!placeOfResidence.trim().isEmpty()) this.placeOfResidence = placeOfResidence;
    }
    /*
     GETTERS AND SETTERS
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPeselNumber() {
        return peselNumber;
    }

    public void setPeselNumber(String peselNumber) {
        this.peselNumber = peselNumber;
    }

    public String getPlaceOfResidence() {
        return placeOfResidence;
    }

    public void setPlaceOfResidence(String placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public Double getSalary() {
        return salary;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public User getUser() {
        return user;
    }

    public void setBirthDate(LocalDate birthDate) {this.birthDate = birthDate;}

    public void setHireDate(LocalDate hireDate) {this.hireDate = hireDate;}

    public void setIdEmployee(Long idEmployee) {this.idEmployee = idEmployee;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setEmployeeType(EmployeeType employeeType) { this.employeeType = employeeType; }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updateEmployee(EmployeeResponse employee, EmployeeType employeeType){
        this.setFirstName(employee.getFirstName());
        this.setLastName(employee.getLastName());
        this.setBirthDate(employee.getBirthDate());
        this.setHireDate(employee.getHireDate());
        this.setSalary(employee.getSalary());
        this.setPhoneNumber(employee.getPhoneNumber());
        this.setPeselNumber(employee.getPeselNumber());
        this.setPlaceOfResidence(employee.getPlaceOfResidence());
        this.setEmployeeType(employeeType);
    }
}
