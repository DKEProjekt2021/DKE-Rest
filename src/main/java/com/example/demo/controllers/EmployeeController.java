//Controller Klasse für die Employee Entity, definiert Methoden zur Datenbankmanipulation hinsichtlich der Employee Tabelle
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
import java.time.Instant;
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

    //Gibt alle Mitarbeiter mit dem eingegebenen Vornamen zurück
    @GetMapping("employee/firstName/{firstName}")
    List<EmployeeEntity> withFirstName(@PathVariable String firstName) {
        List<EmployeeEntity> list = repository.findByFirstName(firstName);
        if (list.isEmpty()) throw new EmployeeRequestNotFoundException("No employee with firstname: " + firstName);
        return repository.findByFirstName(firstName);
    }

    //Gibt alle Mitarbeiter mit der eingegebenen ID zurück
    @GetMapping("employee/id/{id}")
    EmployeeEntity withId(@PathVariable int id) {
        return repository.findById(id).orElseThrow(() -> new EmployeeIDNotFoundException("No employee with ID: ",id));
    }

    //Gibt alle Mitarbeiter mit dem eingegebenen Nachnamen zurück
    @GetMapping("employee/lastName/{lastName}")
    List<EmployeeEntity> withLastName(@PathVariable String lastName) {
        List<EmployeeEntity> list = repository.findByLastName(lastName);
        if (list.isEmpty()) throw new EmployeeRequestNotFoundException("No employee with lastName: " + lastName);
        return repository.findByLastName(lastName);
    }

    //Gibt alle Mitarbeiter mit dem eingegebenen Aktivitätsstatus zurück
    @GetMapping("employee/active/{active}")
    List<EmployeeEntity> areActive(@PathVariable int active) {
        List<EmployeeEntity> list = repository.findByActive(active);
        if (list.isEmpty())
            throw new EmployeeIDNotFoundException("Could not find employees with active status: ", active);
        return repository.findByActive(active);
    }

    //Gibt alle Mitarbeiter die in der eingegebenen Abteilung arbeiten zurück
    @GetMapping("employee/department/{department_Id}")
    List<EmployeeEntity> inDepartment(@PathVariable int department_Id) {
        List<EmployeeEntity> list = repository.findByDepartmentId(department_Id);
        if (list.isEmpty())
            throw new EmployeeIDNotFoundException("Could not find employees for department: ", department_Id);
        return repository.findByDepartmentId(department_Id);
    }
    //Service-Methode um neue Mitarbeiter anzulegen, überprüft Eingaben auf Formatierung
    @PostMapping("/employee")
    EmployeeEntity newEmployeeEntity(@RequestBody EmployeeEntity employeeEntity) {
        if (ObjectUtils.isEmpty(employeeEntity.getSVNR())) {
            throw new EmployeeBadRequestException("SVNR should not be empty!");
        }
        if (employeeEntity.getSVNR().length() != 10 || !employeeEntity.getSVNR().matches("[0-9]{10}")) {
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
        if (employeeEntity.getActive() != 1 && employeeEntity.getActive() != 2) {
            throw new EmployeeBadRequestException("Only 1 or 2 possible for input");
        }

        if (ObjectUtils.isEmpty(employeeEntity.getStart_date())) {
            throw new EmployeeBadRequestException("Start date field should not be empty!");
        }

        if(employeeEntity.getEnd_date() != null) {
            if (employeeEntity.getEnd_date().before(employeeEntity.getStart_date())) {
                throw new EmployeeBadRequestException("End date should not be before start date");
            }
        }

        employeeEntity.setLastChanged(Instant.now());

        if(employeeEntity.getPassword() == null) {
            employeeEntity.setPassword("nopasswordgiven");
        }

        /**
         * wurde kein Loginname manuell vergeben muss dieser auf einen vorrübergehenden Wert gesetzt werden
         * dies hat den Grund, dass ansonsten ein Fehler durch den Service gemeldet wird da das Loginname Feld nicht
         * null sein darf.
         */
        if(employeeEntity.getLogin_name() == null || employeeEntity.getLogin_name().isEmpty()) {
            employeeEntity.setLogin_name("provisional");
        }
        //Das anzulegende Employeeobjekt wird hier zwischengespeichert damit der Zugriff auf das Passwort gewährleistet wird
        //Anschließend wird das Passwort durch die Hashing Methode verschlüsselt und das gesamte Employee Objekt in die DB gespeichert
        EmployeeEntity temp =  repository.save(employeeEntity);


        //wurde kein Loginname vergeben wird dieser anhand des Vornamens, Nachnamens und der ID des Mitarbeiter generiert
        if(temp.getLogin_name() == "provisional") {
            temp.generateLoginName(temp.getEmployeeid());
        }

        if(temp.getPassword() == "nopasswordgiven") {
            temp.generateStartingPassword(Integer.toString(temp.getEmployeeid()));
        }

        temp.setPassword(doHashing(employeeEntity.getPassword()));

        EmployeeEntity toSave =  repository.save(temp);
        return toSave;
    }

    //Service Methode zur Bearbeitung von Mitarbeiter, hiermit können einzelne Felder wie zB nur der Vorname eines Employee
    //geändert werden, oder auch mehrere Felder aufeinmal
    //Überprüft Benutzereingaben auf mögliche Falscheingaben
    @PatchMapping("/employee/{id}")
    public EmployeeEntity patchEmployee(@PathVariable int id, @RequestBody EmployeeEntity newEmployeeData) {
        EmployeeEntity emp = repository.findById(id).orElseThrow(() -> new EmployeeIDNotFoundException("Could not find employee with ID: ", id));

        if (newEmployeeData.getFirstName() != null) {
            if(!newEmployeeData.getFirstName().matches("[a-zA-Z]+")) {
                throw new EmployeeBadRequestException("First name incorrectly formatted!");
            }
            emp.setFirstName(newEmployeeData.getFirstName());
        }

        if (newEmployeeData.getLastName() != null) {
            if(!newEmployeeData.getLastName().matches("[a-zA-Z]+")) {
                throw new EmployeeBadRequestException("Last name incorrectly formatted!");
            }
            emp.setLastName(newEmployeeData.getLastName());
        }

        if (newEmployeeData.getActive() == 1 || newEmployeeData.getActive() == 2) {
            emp.setActive(newEmployeeData.getActive());
        }

        if (newEmployeeData.getActive() != 1 && newEmployeeData.getActive() != 2 && newEmployeeData.getActive() != 0) {
            throw new EmployeeBadRequestException("Only 1 or 2 possible for active");
        }

        if (newEmployeeData.getDepartmentId() != 0) {
            emp.setDepartmentId(newEmployeeData.getDepartmentId());
        }

        if (newEmployeeData.getLogin_name() != null) {
            if(!newEmployeeData.getLogin_name().matches("[A-Za-z0-9]+")) {
                throw new EmployeeBadRequestException("Login name should not be empty");
            }
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
            if (newEmployeeData.getSVNR().length() != 10 || !newEmployeeData.getSVNR().matches("[0-9]{10}")) {
                throw new EmployeeBadRequestException("SVNR formatted incorrectly!");
            }
            emp.setSVNR(newEmployeeData.getSVNR());
        }

        emp.setLastChanged(Instant.now());
        repository.save(emp);
        return emp;
    }

    //Methode zum Löschen eines Employees anhand dessen ID
    @DeleteMapping("/employee/{id}")
    void deleteEmployee(@PathVariable int id) {
       repository.deleteById(id);
    }

    //Methode zum Löschen aller Employees
    @DeleteMapping("/employee")
    void deleteAllEmployees() {
        repository.deleteAll();
    }

    //Methode zum Löschen aller inaktiven Mitarbeiter, also Mitarbeiter mit dem Wert active==2
    @DeleteMapping("employee/inactive/{active}")
    void deleteInactiveEmployees(@PathVariable int active) {
        if (active == 1) {
            throw new EmployeeForbiddenRequest("Active employees cannot be deleted this way!");
        } else {
            List requests = repository.removeByActive(active);
            if (requests.isEmpty()) throw new EmployeeRequestNotFoundException("Not found");
        }
    }



    //Gibt alle Mitarbeiter mit dem eingegebenen Loginnamen zurück
    @GetMapping("employee/loginname/{loginName}")
    List<EmployeeEntity> withUsername(@PathVariable String loginName) {
        List<EmployeeEntity> list = repository.findByLoginName(loginName);
        if (list.isEmpty())
            throw new EmployeeRequestNotFoundException("Could not find employees with username: " + loginName);
        return repository.findByLoginName(loginName);
    }


    //Hashing Methode zur Verschlüsselung des Passwortes eines Employee
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


