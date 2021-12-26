package com.example.demo.controllers;

import com.example.demo.entities.TargetstateEntity;
import com.example.demo.entities.Type;
import com.example.demo.exceptions.*;
import com.example.demo.repositories.TargetstateRepository;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.sql.Timestamp;
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

    @GetMapping("Targetstate/targetconfigid/{targetconfigid}")
    List<TargetstateEntity> withTargetstateTargetconfig_id(@PathVariable int targetconfigid) {
        return repository.findByTargetconfigid(targetconfigid);
    }



    //Getter für active-Feld
    @GetMapping("Targetstate/Date/last_updated/{lastupdated}")
    List <TargetstateEntity> withTargetstateLastUpdated(@PathVariable Date lastupdated) {
        return repository.findByLastupdated(lastupdated);
    }

    //Getter für active-Feld
    @GetMapping("Targetstate/Date/active_since/{activesince}")
    List <TargetstateEntity> withTargetstateActiveSince(@PathVariable Date activesince) {
        return repository.findByActivesince(activesince);
    }

    @GetMapping("Targetstate/id/{targetstateid}")
    TargetstateEntity withTargetstateId(@PathVariable int targetstateid) {
        return repository.findByTargetstateid(targetstateid);
    }

    //Getter für targetConfig-Feld
    @GetMapping("Targetstate/department_id/{departmentid}")
    List <TargetstateEntity> withTargetstateDepartmentId(@PathVariable int departmentid) {
        return repository.findByDepartmentid(departmentid);
    }
    @GetMapping("Targetstate/type/{type}")
    List <TargetstateEntity> withTargetstateType(@PathVariable Type type) {
        return repository.findByType(type);
    }

    @GetMapping("Targetstate/port/{port}")
    List <TargetstateEntity> withTargetstatePort(@PathVariable String port) {
        return repository.findByPort(port);
    }

    @GetMapping("Targetstate/path/{path}")
    List <TargetstateEntity> withTargetstatePath(@PathVariable String path) {
        return repository.findByPath(path);
    }

    @GetMapping("Targetstate/user/{user}")
    List <TargetstateEntity> withTargetstateUser(@PathVariable String user) {
        return repository.findByUser(user);
    }

    @GetMapping("Targetstate/password/{password}")
    List <TargetstateEntity> withTargetstatePassword(@PathVariable String password) {
        return repository.findByPassword(password);
    }

    @GetMapping("Targetstate/hostname/{hostname}")
    List <TargetstateEntity> withTargetstateHostname(@PathVariable String hostname) {
        return repository.findByHostname(hostname);
    }

    @GetMapping("Targetstate/tablename/{tablename}")
    List <TargetstateEntity> withTargetstateTablename(@PathVariable String tablename) {
        return repository.findByTablename(tablename);
    }

    @GetMapping("Targetstate/Timestamp/last_synced/{lastsynced}")
    List <TargetstateEntity> withTargetstateLastUpdated(@PathVariable Timestamp lastsynced) {
        return repository.findByLastsynced(lastsynced);
    }

    @PostMapping("/Targetstate")
    TargetstateEntity newTargetstateEntity(@RequestBody TargetstateEntity targetstateEntity) {
        if(ObjectUtils.isEmpty(targetstateEntity.getTargetstateid())) {
            throw new TargetIDBadRequestException("Targetstate-name should not be empty!");
        }
        if(ObjectUtils.isEmpty(targetstateEntity.getTargetconfigid())) {
            throw new TargetIDBadRequestException("TargetConfig-Id should not be empty!");
        }
        if (ObjectUtils.isEmpty(targetstateEntity.getActive())) {
            throw new TargetIDBadRequestException("Active status field should not be empty!");
        }
        if(ObjectUtils.isEmpty(targetstateEntity.getDepartmentid())) {
            throw new TargetIDBadRequestException("Department-name should not be empty!");
        }
        if(ObjectUtils.isEmpty(targetstateEntity.getPort())) {
            throw new TargetIDBadRequestException("Targetstate-port should not be empty!");
        }
        if(ObjectUtils.isEmpty(targetstateEntity.getPath())) {
            throw new TargetIDBadRequestException("Targetstate-path should not be empty!");
        }
        if(ObjectUtils.isEmpty(targetstateEntity.getUser())) {
            throw new TargetIDBadRequestException("Targetstate-user should not be empty!");
        }
        if(ObjectUtils.isEmpty(targetstateEntity.getPassword())) {
            throw new TargetIDBadRequestException("Targetstate-password should not be empty!");
        }
        if(ObjectUtils.isEmpty(targetstateEntity.getHostname())) {
            throw new TargetIDBadRequestException("Targetstate-hostname should not be empty!");
        }
        if(ObjectUtils.isEmpty(targetstateEntity.getType())) {
            throw new TargetIDBadRequestException("Targetstate-type should not be empty!");
        }
        if(ObjectUtils.isEmpty(targetstateEntity.getTablename())) {
            throw new TargetIDBadRequestException("Targetstate-Tablename should not be empty!");
        }
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        targetstateEntity.setActive(1);
        targetstateEntity.setActivesince(date);
        targetstateEntity.setLastupdated(date);
        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);
        targetstateEntity.setLast_synced(sqlTimestamp);
        return repository.save(targetstateEntity);
    }

    //PatchMapping = Updaten oder Put Mapping(komplett ersetzen jedes Feld wird neu angelegt)
    @PatchMapping("/Targetstate/{id}")
    public TargetstateEntity patchTargetstate(@PathVariable int id, @RequestBody TargetstateEntity newEmployeeData) {
        TargetstateEntity emp = repository.findByTargetstateid(id);
        emp.setDepartmentid(newEmployeeData.getDepartmentid());
        emp.setTargetconfigid(newEmployeeData.getTargetconfigid());
        emp.setType(newEmployeeData.getType());
        emp.setPort(newEmployeeData.getPort());
        emp.setPath(newEmployeeData.getPath());
        emp.setUser(newEmployeeData.getUser());
        emp.setPassword(newEmployeeData.getPassword());
        emp.setHostname(newEmployeeData.getHostname());
        emp.setTablename(newEmployeeData.getTablename());
        emp.setActive(newEmployeeData.getActive());
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        emp.setLastupdated(date);
        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);
        emp.setLast_synced(sqlTimestamp);
        if(emp.getActive()==1){
            emp.setActivesince(date);
        }
        repository.save(emp);
        return emp;
    }

    @DeleteMapping("/Targetstate/{targetstateId}")
    void deleteTargetstate(@PathVariable int targetstateId) {
        repository.deleteById(targetstateId);
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
