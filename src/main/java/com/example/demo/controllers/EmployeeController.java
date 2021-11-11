package com.example.demo.controllers;

import com.example.demo.exceptions.EmployeeBadRequestException;
import com.example.demo.entities.EmployeeEntity;
import com.example.demo.exceptions.EmployeeForbiddenRequest;
import com.example.demo.exceptions.EmployeeIDNotFoundException;
import com.example.demo.exceptions.EmployeeRequestNotFoundException;
import com.example.demo.repositories.EmployeeRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employee")
    List<EmployeeEntity> all() {
        return repository.findAll();
    }

    @GetMapping("employee/firstName/{firstName}")
    List<EmployeeEntity> withFirstName(@PathVariable String firstName) {
        List<EmployeeEntity> list = repository.findByFirstName(firstName);
        if (list.isEmpty()) throw new EmployeeRequestNotFoundException("No employee with firstname: " + firstName);
        return repository.findByFirstName(firstName);
    }

    @GetMapping("employee/lastName/{lastName}")
    List<EmployeeEntity> withLastName(@PathVariable String lastName) {
        List<EmployeeEntity> list = repository.findByFirstName(lastName);
        if (list.isEmpty()) throw new EmployeeRequestNotFoundException("No employee with lastName: " + lastName);
        return repository.findByLastName(lastName);
    }

    @GetMapping("employee/active/{active}")
    List<EmployeeEntity> areActive(@PathVariable int active) {
        List<EmployeeEntity> list = repository.findByActive(active);
        if (list.isEmpty())
            throw new EmployeeIDNotFoundException("Could not find employees with active status: ", active);
        return repository.findByActive(active);
    }

    @GetMapping("employee/department/{department}")
    List<EmployeeEntity> inDepartment(@PathVariable int department) {
        List<EmployeeEntity> list = repository.findByDepartment(department);
        if (list.isEmpty())
            throw new EmployeeIDNotFoundException("Could not find employees for department: ", department);
        return repository.findByActive(department);
    }

    @PostMapping("/employee")
    EmployeeEntity newEmployeeEntity(@RequestBody EmployeeEntity employeeEntity) {
        if (ObjectUtils.isEmpty(employeeEntity.getSVNR())) {
            throw new EmployeeBadRequestException("SVNR should not be empty!");
        }
        if (Integer.toString(employeeEntity.getSVNR()).length() != 10 || !Integer.toString(employeeEntity.getSVNR()).matches("[0-9]{10}")) {
            throw new EmployeeBadRequestException("SVNR formatted incorrectly!");
        }

        if (ObjectUtils.isEmpty(employeeEntity.getFirstName())) {
            throw new EmployeeBadRequestException("Firstname should not be empty!");
        }
        if (!employeeEntity.getFirstName().matches("[a-zA-Z]*")) {
            throw new EmployeeBadRequestException("First name incorrectly formatted!");
        }

        if (ObjectUtils.isEmpty(employeeEntity.getLastName())) {
            throw new EmployeeBadRequestException("Lastname should not be empty!");
        }
        if (!employeeEntity.getLastName().matches("[a-zA-Z]*")) {
            throw new EmployeeBadRequestException("Last name incorrectly formatted!");
        }

        if (ObjectUtils.isEmpty(employeeEntity.getActive())) {
            throw new EmployeeBadRequestException("Active status field should not be empty!");
        }
        if (employeeEntity.getActive() != 0 && employeeEntity.getActive() != 1) {
            throw new EmployeeBadRequestException("Only 0 or 1 possible for input");
        }

        if (ObjectUtils.isEmpty(employeeEntity.getStart_date())) {
            throw new EmployeeBadRequestException("Start date field should not be empty!");
        }
        if (employeeEntity.getEnd_date().before(employeeEntity.getStart_date())) {
            throw new EmployeeBadRequestException("End date should not be before start date");
        }
        if(employeeEntity.getEnd_date().after(Date.from(Instant.now()))) {
            throw new EmployeeBadRequestException("End date can not be after today");
        }


        employeeEntity.setSVNR(employeeEntity.getSVNR());
        employeeEntity.generateLoginName();
        employeeEntity.generateStartingPassword();

        return repository.save(employeeEntity);
    }

    @PatchMapping("/employee/{id}")
    public EmployeeEntity patchEmployee(@PathVariable int id, @RequestBody EmployeeEntity newEmployeeData) {
        EmployeeEntity emp = repository.findById(id).orElseThrow(() -> new EmployeeIDNotFoundException("Could not find employee with ID: ", id));


        if (newEmployeeData.getFirstName() != null) {
            emp.setFirstName(newEmployeeData.getFirstName());
        }

        if (newEmployeeData.getLastName() != null) {
            emp.setLastName(newEmployeeData.getLastName());
        }
        if (newEmployeeData.getActive() == 0 || newEmployeeData.getActive() == 1) {
            emp.setActive(newEmployeeData.getActive());
        }
        if (newEmployeeData.getLogin_name() != null) {
            emp.setLogin_name(newEmployeeData.getLogin_name());
        }
        if (newEmployeeData.getPassword() != null) {
            emp.setPassword(newEmployeeData.getPassword());
        }
        if (newEmployeeData.getStart_date() != null) {
            emp.setStart_date(newEmployeeData.getStart_date());
        }

        if (newEmployeeData.getEnd_date() != null) {
            emp.setEnd_date(newEmployeeData.getEnd_date());
        }

        if(newEmployeeData.getSVNR() != 0) {
            throw new EmployeeBadRequestException("Not allowed!");
        }

        repository.save(emp);
        return emp;
    }

    @DeleteMapping("/employee/{id}")
    void deleteEmployee(@PathVariable int id) {
        repository.deleteById(id);
    }

    @DeleteMapping("/employee")
    void deleteAllEmployees() {
        repository.deleteAll();
    }

    @DeleteMapping("employee/inactive/{active}")
    void deleteInactiveEmployees(@PathVariable int active) {
        if (active == 1) {
            throw new EmployeeForbiddenRequest("Active employees cannot be deleted this way!");
        } else {
            List requests = repository.removeByActive(active);
        }
    }
    //One way hash method for Passwords
    public String doHashing(String password){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] resultByteArray = messageDigest.digest();
            StringBuilder sb = new StringBuilder();

            for(byte b : resultByteArray){
                sb.append(String.format("%02x",b));
            }
            return sb.toString();

        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }

}


