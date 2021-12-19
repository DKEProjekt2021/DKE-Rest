package com.example.demo.controllers;

import com.example.demo.entities.TargetConfigEntity;
import com.example.demo.exceptions.TargetIDBadRequestException;
import com.example.demo.exceptions.TargetIDNotFoundException;
import com.example.demo.repositories.TargetConfigRepository;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class TargetConfigController {

    private final TargetConfigRepository repository;

    public TargetConfigController(TargetConfigRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/targetconfig")
    List<TargetConfigEntity> all() {
        return repository.findAll();
    }

    @GetMapping("/targetconfig/id/{id}")
    TargetConfigEntity withId(@PathVariable int id) {
        return repository.findById(id).orElseThrow(() -> new TargetIDNotFoundException("No TargetConfig with ID: "+id));
    }


    @DeleteMapping("/targetconfig")
    void deleteAllConfigs() {
        repository.deleteAll();
    }


    @PostMapping("/targetconfig")
    TargetConfigEntity newTargetConfigEntity(@RequestBody TargetConfigEntity targetConfigEntity) {

        if (targetConfigEntity.getCheckEmployeeId() != 0 &&  targetConfigEntity.getCheckEmployeeId() != 1) {
            throw new TargetIDBadRequestException("This field can either be 0 or 1");
        }

        if (targetConfigEntity.getCheckLastName() != 0 &&  targetConfigEntity.getCheckLastName() != 1) {
            throw new TargetIDBadRequestException("This field can either be 0 or 1");
        }
        if (targetConfigEntity.getCheckFirstName() != 0 &&  targetConfigEntity.getCheckFirstName()  != 1) {
            throw new TargetIDBadRequestException("This field can either be 0 or 1");
        }
        if (targetConfigEntity.getCheckLoginName() != 0 &&  targetConfigEntity.getCheckLoginName()  != 1) {
            throw new TargetIDBadRequestException("This field can either be 0 or 1");
        }
        if (targetConfigEntity.getCheck_password() != 0 &&  targetConfigEntity.getCheck_password()  != 1) {
            throw new TargetIDBadRequestException("This field can either be 0 or 1");
        }
        if (targetConfigEntity.getCheckStartDate() != 0 &&  targetConfigEntity.getCheckStartDate()  != 1) {
            throw new TargetIDBadRequestException("This field can either be 0 or 1");
        }
        if (targetConfigEntity.getCheckEndDate() != 0 &&  targetConfigEntity.getCheckEndDate()  != 1) {
            throw new TargetIDBadRequestException("This field can either be 0 or 1");
        }



        return repository.save(targetConfigEntity);
    }

    @PatchMapping("/targetconfig/{id}")
    public TargetConfigEntity patchTargetConfig(@PathVariable int id, @RequestBody TargetConfigEntity newTargetConfig) {
        TargetConfigEntity configToUpdate = repository.findById(id).orElseThrow(() -> new TargetIDNotFoundException("Could not find target config with ID: " +id));

        if(newTargetConfig.getCheckEmployeeId() == 0 || newTargetConfig.getCheckEmployeeId() == 1)
            configToUpdate.setCheckEmployeeId(newTargetConfig.getCheckEmployeeId());
        else throw new TargetIDBadRequestException("This field can only be 0 or 1");

        if(newTargetConfig.getCheckFirstName() == 0 || newTargetConfig.getCheckFirstName() == 1)
            configToUpdate.setCheckFirstName(newTargetConfig.getCheckFirstName());
        else throw new TargetIDBadRequestException("This field can only be 0 or 1");

        if(newTargetConfig.getCheckLastName() == 0 || newTargetConfig.getCheckLastName() == 1)
            configToUpdate.setCheckLastName(newTargetConfig.getCheckLastName());
        else throw new TargetIDBadRequestException("This field can only be 0 or 1");

        if(newTargetConfig.getCheckLoginName() == 0 || newTargetConfig.getCheckLoginName() == 1)
            configToUpdate.setCheckLoginName(newTargetConfig.getCheckLoginName());
        else throw new TargetIDBadRequestException("This field can only be 0 or 1");

        if(newTargetConfig.getCheck_password() == 0 || newTargetConfig.getCheck_password() == 1)
            configToUpdate.setCheck_password(newTargetConfig.getCheck_password());
        else throw new TargetIDBadRequestException("This field can only be 0 or 1");

        if(newTargetConfig.getCheckStartDate() == 0 || newTargetConfig.getCheckStartDate() == 1)
            configToUpdate.setCheckStartDate(newTargetConfig.getCheckStartDate());
        else throw new TargetIDBadRequestException("This field can only be 0 or 1");

        if(newTargetConfig.getCheckEndDate() == 0 || newTargetConfig.getCheckEndDate() == 1)
            configToUpdate.setCheckEndDate(newTargetConfig.getCheckEndDate());
        else throw new TargetIDBadRequestException("This field can only be 0 or 1");

        if(newTargetConfig.getCheckSVNR() == 0 || newTargetConfig.getCheckSVNR() == 1)
            configToUpdate.setCheckSVNR(newTargetConfig.getCheckSVNR());
        else throw new TargetIDBadRequestException("This field can only be 0 or 1");



        repository.save(configToUpdate);
        return configToUpdate;
    }

    @DeleteMapping("/targetconfig/delete/{id}")
    void deleteDepartment(@PathVariable int id) {
        repository.deleteById(id);
    }




















}
