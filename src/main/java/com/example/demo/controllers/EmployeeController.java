package com.example.demo.controllers;

import com.example.demo.exceptions.EmployeeBadRequestException;
import com.example.demo.entities.EmployeeEntity;
import com.example.demo.exceptions.EmployeeForbiddenRequest;
import com.example.demo.exceptions.EmployeeRequestNotFoundException;
import com.example.demo.repositories.EmployeeRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

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
    List <EmployeeEntity> withFirstName(@PathVariable String firstName) {
        return repository.findByFirstName(firstName);
    }
    @GetMapping("employee/lastName/{lastName}")
    List <EmployeeEntity> withLastName(@PathVariable String lastName) {
        return repository.findByLastName(lastName);
    }

    @GetMapping("employee/active/{active}")
    List <EmployeeEntity> areActive(@PathVariable int active) {
        return repository.findByActive(active);
    }

    @GetMapping("employee/department/{department}")
    List <EmployeeEntity> inDepartment(@PathVariable int department) {
        return repository.findByDepartment(department);
    }

    @PostMapping("/employee")
    EmployeeEntity newEmployeeEntity(@RequestBody EmployeeEntity employeeEntity) {
        if(ObjectUtils.isEmpty(employeeEntity.getSVNR())) {
            throw new EmployeeBadRequestException("SVNR should not be empty!");
        }
        if(ObjectUtils.isEmpty(employeeEntity.getFirstName())) {
            throw new EmployeeBadRequestException("Firstname should not be empty!");
        }
        if(ObjectUtils.isEmpty(employeeEntity.getLastName())) {
            throw new EmployeeBadRequestException("Lastname should not be empty!");
        }

        if(ObjectUtils.isEmpty(employeeEntity.getActive())) {
            throw new EmployeeBadRequestException("Active status field should not be empty!");
        }

        if(ObjectUtils.isEmpty(employeeEntity.getStart_date())) {
            throw new EmployeeBadRequestException("Start date field should not be empty!");
        }
        if(!employeeEntity.getFirstName().matches ("\\w+\\.?")) {
            throw new EmployeeBadRequestException("First name incorrectly formatted!");
        }
        if(!employeeEntity.getLastName().matches ("\\w+\\.?")) {
            throw new EmployeeBadRequestException("Last name incorrectly formatted!");
        }
        if(employeeEntity.getActive() != 0 && employeeEntity.getActive() != 1) {
            throw new EmployeeBadRequestException("Only 0 or 1 possible for input");
        }
        employeeEntity.setSVNR(employeeEntity.getSVNR());
        return repository.save(employeeEntity);
    }

    @PatchMapping("/employee/{id}")
    public EmployeeEntity patchEmployee(@PathVariable int id, @RequestBody EmployeeEntity newEmployeeData) {
        EmployeeEntity emp = repository.findById(id).orElseThrow(() -> new EmployeeRequestNotFoundException(id));

            if(newEmployeeData.getFirstName() != null) {
                emp.setFirstName(newEmployeeData.getFirstName());
            }
        if(newEmployeeData.getLastName() != null) {
            throw new EmployeeBadRequestException("Not allowed brother!");
        }
        if(newEmployeeData.getActive() != 0 && newEmployeeData.getActive() != 1) {
            emp.setFirstName(newEmployeeData.getFirstName());
        }
        if(newEmployeeData.getLogin_name() != null) {
            emp.setLogin_name(newEmployeeData.getLogin_name());
        }
        if(newEmployeeData.getPassword() != null) {
            emp.setPassword(newEmployeeData.getPassword());
        }
        if(newEmployeeData.getStart_date() != null) {
            emp.setStart_date(newEmployeeData.getStart_date());
        }

        if(newEmployeeData.getEnd_date() != null) {
            emp.setEnd_date(newEmployeeData.getEnd_date());
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
        if(active == 1) {
            throw new EmployeeForbiddenRequest("Active employees cannot be deleted this way!");
        } else {
          List requests = repository.removeByActive(active);
        }
    }






}
