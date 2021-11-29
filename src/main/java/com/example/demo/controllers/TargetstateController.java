package com.example.demo.controllers;

import com.example.demo.entities.TargetstateEntity;
import com.example.demo.exceptions.*;
import com.example.demo.repositories.TargetstateRepository;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class TargetstateController {

    private final TargetstateRepository repository;

    public TargetstateController(TargetstateRepository repository) {
        this.repository = repository;
    }

    //gibt alle departments zurück
    @GetMapping("/Targetstate")
    List<TargetstateEntity> all() {
        return repository.findAll();
    }

    //Getter für active-Feld
    @GetMapping("Targetstate/active/{active}")
    List <TargetstateEntity> withTargetstateActive(@PathVariable int active) {
        return repository.findByActive(active);
    }

    @GetMapping("Targetstate/targetconfig_id/{targetconfig_id}")
    TargetstateEntity withTargetstateTargetconfig_id(@PathVariable int targetconfigid) {
        return repository.findByTargetconfigid(targetconfigid);
    }

    //Getter für active-Feld
    @GetMapping("Targetstate/Date/last_updated/{lastupdated}")
    List <TargetstateEntity> withTargetstateLastUpdated(@PathVariable Date lastUpdated) {
        return repository.findByLastupdated(lastUpdated);
    }

    //Getter für active-Feld
    @GetMapping("Targetstate/Date/active_since/{activesince}")
    List <TargetstateEntity> withTargetstateActiveSince(@PathVariable Date activeSince) {
        return repository.findByActivesince(activeSince);
    }

    @GetMapping("Targetstate/id/{targetstateid}")
    TargetstateEntity withTargetstateId(@PathVariable int id) {
        return repository.findByTargetstateid(id);
    }

    //Getter für targetConfig-Feld
    @GetMapping("Targetstate/department_id/{departmentid}")
    List <TargetstateEntity> withTargetstateDepartmentId(@PathVariable int departmentid) {
        return repository.findByDepartmentid(departmentid);
    }

    @PostMapping("/Targetstate")
    TargetstateEntity newTargetstateEntity(@RequestBody TargetstateEntity targetstateEntity) {
        if(ObjectUtils.isEmpty(targetstateEntity.getTargetstateid())) {
            throw new DepartmentBadRequestException("Targetstate-name should not be empty!");
        }
        if(ObjectUtils.isEmpty(targetstateEntity.getTargetconfigid())) {
            throw new DepartmentBadRequestException("TargetConfig-Id should not be empty!");
        }
        if (ObjectUtils.isEmpty(targetstateEntity.getActive())) {
            throw new EmployeeBadRequestException("Active status field should not be empty!");
        }
        if(ObjectUtils.isEmpty(targetstateEntity.getDepartmentid())) {
            throw new DepartmentBadRequestException("Department-name should not be empty!");
        }
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        targetstateEntity.setActivesince(date);
        targetstateEntity.setLastupdated(date);
        return repository.save(targetstateEntity);
    }

    //PatchMapping = Updaten oder Put Mapping(komplett ersetzen jedes Feld wird neu angelegt)
    @PatchMapping("/Targetstate/{id}")
    public TargetstateEntity patchTargetstate(@PathVariable int id, @RequestBody TargetstateEntity newEmployeeData) {
        TargetstateEntity emp = repository.findByTargetstateid(id);
        emp.setActive(newEmployeeData.getActive());
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        emp.setLastupdated(date);
        if(emp.getActive()==1){
            emp.setActivesince(date);
        }
        repository.save(emp);
        return emp;
    }

    @DeleteMapping("/Targetstate/{targetstateId}")
    void deleteTargetstate(@PathVariable int targetstateid) {
        repository.deleteById(targetstateid);
    }

    @DeleteMapping("/Targetstate")
    void deleteAllTargetstate() {
        repository.deleteAll();
    }

    @DeleteMapping("Targetstate/inactive/{active}")
    void deleteInactiveTargetstates(@PathVariable int active) {
        if (active == 1) {
            throw new TargetIDBadRequestException("Active Targetstates cannot be deleted this way!");
        } else {
            List requests = repository.removeByActive(active);
        }
    }
}
