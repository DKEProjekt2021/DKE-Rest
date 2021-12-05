package com.example.demo.controllers;

import com.example.demo.exceptions.EmployeeBadRequestException;
import com.example.demo.entities.EmployeeEntity;
import com.example.demo.exceptions.EmployeeForbiddenRequest;
import com.example.demo.exceptions.EmployeeIDNotFoundException;
import com.example.demo.exceptions.EmployeeRequestNotFoundException;
import com.example.demo.repositories.EmployeeRepository;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin(origins = "*")
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

    @GetMapping("employee/id/{id}")
    EmployeeEntity withId(@PathVariable int id) {
        return repository.findById(id).orElseThrow(() -> new EmployeeIDNotFoundException("No employee with ID: ",id));
    }

    @GetMapping("employee/lastName/{lastName}")
    List<EmployeeEntity> withLastName(@PathVariable String lastName) {
        List<EmployeeEntity> list = repository.findByLastName(lastName);
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
        return repository.findByDepartment(department);
    }

    @PostMapping("/employee")
    EmployeeEntity newEmployeeEntity(@RequestBody EmployeeEntity employeeEntity) {
        if (ObjectUtils.isEmpty(employeeEntity.getSVNR())) {
            throw new EmployeeBadRequestException("SVNR should not be empty!");
        }
        if (employeeEntity.getSVNR().length() != 10 || !employeeEntity.getSVNR().matches("[0-9]{10}")) {
            System.out.println(employeeEntity.getSVNR().length());
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

        if(employeeEntity.getEnd_date() != null) {
            if (employeeEntity.getEnd_date().before(employeeEntity.getStart_date())) {
                throw new EmployeeBadRequestException("End date should not be before start date");
            }
        }

        if(employeeEntity.getDepartment() == 0) {
            throw new EmployeeBadRequestException("Department field should not be empty");
        }

        employeeEntity.generateLoginName(1);
        employeeEntity.generateStartingPassword(Integer.toString(employeeEntity.getEmployeeid()));

        //to get the password from the employee
        EmployeeEntity emp =  repository.save(employeeEntity);
        emp.generateStartingPassword(Integer.toString(emp.getEmployeeid()));
        emp.setPassword(doHashing(employeeEntity.getPassword()));
        emp.generateLoginName(emp.getEmployeeid());
        EmployeeEntity toSave =  repository.save(emp);
        return toSave;
    }

    @PatchMapping("/employee/{id}")
    public EmployeeEntity patchEmployee(@PathVariable int id, @RequestBody EmployeeEntity newEmployeeData) {
        EmployeeEntity emp = repository.findById(id).orElseThrow(() -> new EmployeeIDNotFoundException("Could not find employee with ID: ", id));

        if (newEmployeeData.getFirstName() != null) {
            if(!newEmployeeData.getFirstName().matches("[a-zA-Z]*")) {
                throw new EmployeeBadRequestException("First name incorrectly formatted!");
            }
            emp.setFirstName(newEmployeeData.getFirstName());
        }

        if (newEmployeeData.getLastName() != null) {
            if(!newEmployeeData.getLastName().matches("[a-zA-Z]*")) {
                throw new EmployeeBadRequestException("Last name incorrectly formatted!");
            }
            emp.setLastName(newEmployeeData.getLastName());
        }

        if (newEmployeeData.getActive() == 0 || newEmployeeData.getActive() == 1) {
            emp.setActive(newEmployeeData.getActive());
        }
        if (newEmployeeData.getActive() != 0 && newEmployeeData.getActive() != 1) {
            throw new EmployeeBadRequestException("Only 0 or 1 possible for input");
        }

        if (newEmployeeData.getDepartment() != 0) {
            emp.setDepartment(newEmployeeData.getDepartment());
        }

        if (newEmployeeData.getLogin_name() != null) {
            emp.setLogin_name(newEmployeeData.getLogin_name());
        }
        if (newEmployeeData.getPassword() != null) {
            emp.setPassword(doHashing(newEmployeeData.getPassword()));
        }
        if (newEmployeeData.getStart_date() != null) {
            emp.setStart_date(newEmployeeData.getStart_date());
        }

        if (newEmployeeData.getEnd_date() != null) {
            emp.setEnd_date(newEmployeeData.getEnd_date());
        }

        if(newEmployeeData.getStart_date() != null && emp.getEnd_date() != null) {
            if(newEmployeeData.getStart_date().after(emp.getEnd_date())) {
                throw new EmployeeBadRequestException("Start date cannot be after end date");
            }
        }
        if(newEmployeeData.getEnd_date() != null && emp.getStart_date() != null) {
            if(newEmployeeData.getEnd_date().before(emp.getStart_date())) {
                throw new EmployeeBadRequestException("End date cannot be before start date");
            }
        }

        if(newEmployeeData.getSVNR() != null) {
            if (!newEmployeeData.getSVNR().isEmpty()) {
                throw new EmployeeBadRequestException("Not allowed to change the svnr of an employee!");
            }
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
            if (requests.isEmpty()) throw new EmployeeRequestNotFoundException("Not found");
        }
    }



    //functions for login
    @GetMapping("employee/loginname/{loginName}")
    List<EmployeeEntity> withUsername(@PathVariable String loginName) {
        List<EmployeeEntity> list = repository.findByLoginName(loginName);
        if (list.isEmpty())
            throw new EmployeeRequestNotFoundException("Could not find employees with username: " + loginName);
        return repository.findByLoginName(loginName);
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


