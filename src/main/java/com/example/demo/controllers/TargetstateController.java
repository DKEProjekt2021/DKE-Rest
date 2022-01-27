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

    //Gibt alle Targetstates mit allen Variablen als Liste zurück. Kann für darstellung in Tabellen verwendet werden.
    @GetMapping("/Targetstate")
    List<TargetstateEntity> all() {
        return repository.findAll();
    }

    //Folgende Get Mappings sind für alle Variablen eines bestimmten Targetstates. Ansprechbar ist jedes mapping über den grünen Pfad als HTTP Request
    //Jede Variable des Targetstates ist abgedeckt. Gewisse Variablen wie last_updated, active_since oder last synced generieren sich in post/patch Mapping von selbst da sie für Zeitpunkte oder ein Datum verwendet werden.
    @GetMapping("Targetstate/active/{active}")
    List <TargetstateEntity> withTargetstateActive(@PathVariable int active) {
        return repository.findByActive(active);
    }

    //Getter für targetConfigID Feld
    @GetMapping("Targetstate/targetconfigid/{targetconfigid}")
    List<TargetstateEntity> withTargetstateTargetconfig_id(@PathVariable int targetconfigid) {
        return repository.findByTargetconfigid(targetconfigid);
    }

    //Getter für lastupdated Feld
    @GetMapping("Targetstate/Date/last_updated/{lastupdated}")
    List <TargetstateEntity> withTargetstateLastUpdated(@PathVariable Date lastupdated) {
        return repository.findByLastupdated(lastupdated);
    }

    //Getter für active-since-Feld
    @GetMapping("Targetstate/Date/active_since/{activesince}")
    List <TargetstateEntity> withTargetstateActiveSince(@PathVariable Date activesince) {
        return repository.findByActivesince(activesince);
    }
    //Getter für targetstateID Feld
    @GetMapping("Targetstate/id/{targetstateid}")
    TargetstateEntity withTargetstateId(@PathVariable int targetstateid) {
        return repository.findByTargetstateid(targetstateid);
    }

    //Getter für departmentID Feld
    @GetMapping("Targetstate/department_id/{departmentid}")
    List <TargetstateEntity> withTargetstateDepartmentId(@PathVariable int departmentid) {
        return repository.findByDepartmentid(departmentid);
    }
    //Getter für TYPE (CSV; MYSQL oder LDAP) Feld
    @GetMapping("Targetstate/type/{type}")
    List <TargetstateEntity> withTargetstateType(@PathVariable String type) {
        return repository.findByType(type);
    }

    //Getter für PORT Feld
    @GetMapping("Targetstate/port/{port}")
    List <TargetstateEntity> withTargetstatePort(@PathVariable String port) {
        return repository.findByPort(port);
    }

    //Getter für Path Feld, hier steht der Pfad des Targetstates drin.
    @GetMapping("Targetstate/path/{path}")
    List <TargetstateEntity> withTargetstatePath(@PathVariable String path) {
        return repository.findByPath(path);
    }
    //Getter für Usernamen Feld
    @GetMapping("Targetstate/user/{user}")
    List <TargetstateEntity> withTargetstateUser(@PathVariable String user) {
        return repository.findByUser(user);
    }


    //Getter für passwort des Usernamen
    @GetMapping("Targetstate/password/{password}")
    List <TargetstateEntity> withTargetstatePassword(@PathVariable String password) {
        return repository.findByPassword(password);
    }

    //Getter für den Hostnamen
    @GetMapping("Targetstate/hostname/{hostname}")
    List <TargetstateEntity> withTargetstateHostname(@PathVariable String hostname) {
        return repository.findByHostname(hostname);
    }

    //Getter für Tabellennamen Feld
    @GetMapping("Targetstate/tablename/{tablename}")
    List <TargetstateEntity> withTargetstateTablename(@PathVariable String tablename) {
        return repository.findByTablename(tablename);
    }
    //Getter für last_sync Feld. Wert generiert sich automatisch in den Post/Patchmethoden
    @GetMapping("Targetstate/Timestamp/last_synced/{lastsynced}")
    List <TargetstateEntity> withTargetstateLastUpdated(@PathVariable Timestamp lastsynced) {
        return repository.findByLastsynced(lastsynced);
    }
    //Mapping für das Erstellen eines neuen Targetstates. Active, Active since, last updatedm last synced generieren sich mit dem jeweiligen Datum/Timestamp automatisch und müssen nicht übergeben werden.
    //Je nach Targetstate Type werden verschiedene Elemente zur Erstellung eines Targetstates verlangt. Jeder Typ benötigt andere Variablen.
    @PostMapping("/Targetstate")
    TargetstateEntity newTargetstateEntity(@RequestBody TargetstateEntity targetstateEntity) {
        if(ObjectUtils.isEmpty(targetstateEntity.getType())) {
            throw new TargetIDBadRequestException("Targetstate-type should not be empty!");
        }else {
            if (targetstateEntity.getType().equals("LDAP")) {
                if (ObjectUtils.isEmpty(targetstateEntity.getTargetstateid())) {
                    throw new TargetIDBadRequestException("Targetstate-name should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getTargetconfigid())) {
                    throw new TargetIDBadRequestException("TargetConfig-Id should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getDepartmentid())) {
                    throw new TargetIDBadRequestException("Department-name should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getPath())) {
                    throw new TargetIDBadRequestException("Targetstate-path should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getPort())) {
                    throw new TargetIDBadRequestException("Targetstate-port should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getUser())) {
                    throw new TargetIDBadRequestException("Targetstate-user should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getPassword())) {
                    throw new TargetIDBadRequestException("Targetstate-password should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getHostname())) {
                    throw new TargetIDBadRequestException("Targetstate-hostname should not be empty!");
                }

            } else if (targetstateEntity.getType().equals("CSV")) {
                if (ObjectUtils.isEmpty(targetstateEntity.getTargetstateid())) {
                    throw new TargetIDBadRequestException("Targetstate-name should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getTargetconfigid())) {
                    throw new TargetIDBadRequestException("TargetConfig-Id should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getDepartmentid())) {
                    throw new TargetIDBadRequestException("Department-name should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getPath())) {
                    throw new TargetIDBadRequestException("Targetstate-path should not be empty!");
                }
            } else if (targetstateEntity.getType().equals("JSON")) {
                if (ObjectUtils.isEmpty(targetstateEntity.getTargetstateid())) {
                    throw new TargetIDBadRequestException("Targetstate-name should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getTargetconfigid())) {
                    throw new TargetIDBadRequestException("TargetConfig-Id should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getPath())) {
                    throw new TargetIDBadRequestException("Targetstate-path should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getDepartmentid())) {
                    throw new TargetIDBadRequestException("Department-name should not be empty!");
                }
            } else if (targetstateEntity.getType().equals("MYSQL")) {
                if (ObjectUtils.isEmpty(targetstateEntity.getTargetstateid())) {
                    throw new TargetIDBadRequestException("Targetstate-name should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getTargetconfigid())) {
                    throw new TargetIDBadRequestException("TargetConfig-Id should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getDepartmentid())) {
                    throw new TargetIDBadRequestException("Department-name should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getPort())) {
                    throw new TargetIDBadRequestException("Targetstate-port should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getPath())) {
                    throw new TargetIDBadRequestException("Targetstate-path should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getUser())) {
                    throw new TargetIDBadRequestException("Targetstate-user should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getPassword())) {
                    throw new TargetIDBadRequestException("Targetstate-password should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getHostname())) {
                    throw new TargetIDBadRequestException("Targetstate-hostname should not be empty!");
                }
                if (ObjectUtils.isEmpty(targetstateEntity.getTablename())) {
                    throw new TargetIDBadRequestException("Targetstate-Tablename should not be empty!");
                }
            }
        }
        //Bei erstellung eines Targetstates wird dieser automatisch auf Active gesetzt. Ebenfalls wird active since und last updated auf das heutige Datum gesetzt.
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        targetstateEntity.setActive(1);
        targetstateEntity.setActivesince(date);
        targetstateEntity.setLastupdated(date);
        //long now = System.currentTimeMillis();
        //Timestamp sqlTimestamp = new Timestamp(now);
        //targetstateEntity.setLast_synced(sqlTimestamp);
        return repository.save(targetstateEntity);
    }

    //PatchMapping = Updaten oder Put Mapping(komplett ersetzen jedes Feld wird neu angelegt)
    //Mit diesem Mapping können bestehende Targetstates über ihre Targetstate ID abgeändert werden. Autogenerierende werte wie last_updated generieren sich hierbei wieder automatisch je nach Zeit/Datum bei einer Änderung.
    @PatchMapping("/Targetstate/{id}")
    public TargetstateEntity patchTargetstate(@PathVariable int id, @RequestBody TargetstateEntity newEmployeeData) {
        TargetstateEntity emp = repository.findByTargetstateid(id);
        if(newEmployeeData.getDepartmentid() != 0){
            emp.setDepartmentid(newEmployeeData.getDepartmentid());
        }
        if(newEmployeeData.getType()!=null){
            emp.setType(newEmployeeData.getType());
        }
        if(newEmployeeData.getTargetconfigid() != 0) {
            emp.setTargetconfigid(newEmployeeData.getTargetconfigid());
        }
        if(newEmployeeData.getPath() != null){
            emp.setPath(newEmployeeData.getPath());
        }
        if(newEmployeeData.getPort() != null){
            emp.setPort(newEmployeeData.getPort());
        }
        if(newEmployeeData.getUser() != null){
            emp.setUser(newEmployeeData.getUser());
        }
        if(newEmployeeData.getPassword() != null){
            emp.setPassword(newEmployeeData.getPassword());
        }
        if(newEmployeeData.getHostname() != null){
            emp.setHostname(newEmployeeData.getHostname());
        }
        if(newEmployeeData.getTablename() != null){
            emp.setTablename(newEmployeeData.getTablename());
        }
        if(newEmployeeData.getActive() != 0){
            emp.setActive(newEmployeeData.getActive());
        }
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        emp.setLastupdated(date);
        if(emp.getActive()==1){
            emp.setActivesince(date);
        }else{
            emp.setActivesince(null);
        }
        repository.save(emp);
        return emp;
    }
    //Löschen von Targetstates über Ihre ID
    @DeleteMapping("/Targetstate/{targetstateId}")
    void deleteTargetstate(@PathVariable int targetstateId) {
        repository.deleteById(targetstateId);
    }

    //Löschen aller Targetstates
    @DeleteMapping("/Targetstate")
    void deleteAllTargetstate() {
        repository.deleteAll();
    }

    //Löschen aller inactive Targetstates.
    @DeleteMapping("Targetstate/inactive/{active}")
    void deleteInactiveTargetstates(@PathVariable int active) {
        if (active == 1) {
            throw new TargetIDBadRequestException("Active Targetstates cannot be deleted this way!");
        } else {
            List requests = repository.removeByActive(active);
        }
    }
}
