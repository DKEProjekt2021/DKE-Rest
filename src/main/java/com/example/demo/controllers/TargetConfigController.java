package com.example.demo.controllers;

import com.example.demo.entities.EmployeeEntity;
import com.example.demo.entities.TargetConfigEntity;
import com.example.demo.exceptions.EmployeeBadRequestException;
import com.example.demo.exceptions.EmployeeIDNotFoundException;
import com.example.demo.exceptions.TargetIDBadRequestException;
import com.example.demo.exceptions.TargetIDNotFoundException;
import com.example.demo.repositories.TargetConfigRepository;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("targetconfig/type/{type}")
    List<TargetConfigEntity> withType(@PathVariable String type) {
        List<TargetConfigEntity> list =  repository.findByType(type);
        if(list.isEmpty()) {
            throw new TargetIDNotFoundException("No targetconfig found with type: " + type);
        }
        return list;
    }

    @GetMapping("targetconfig/protocol/{protocol}")
    List<TargetConfigEntity> withProtocol(@PathVariable String protocol) {
        List<TargetConfigEntity> list =  repository.findByProtocol(protocol);
        if(list.isEmpty()) {
            throw new TargetIDNotFoundException("No targetconfig found with protocol: " + protocol);
        }
        return list;
    }

    @DeleteMapping("/targetconfig")
    void deleteAllConfigs() {
        repository.deleteAll();
    }

    @DeleteMapping("targetconfig/{type}")
    void deleteByType(@PathVariable String type) {
        List requests = repository.removeByType(type);
        if (requests.isEmpty()) throw new TargetIDNotFoundException("No target config with type: " + type);
    }

    @DeleteMapping("targetconfig/{protocol}")
    void deleteByProtocol(@PathVariable String protocol) {
        List requests = repository.removeByProtocol(protocol);
        if (requests.isEmpty()) throw new TargetIDNotFoundException("No target config with protocol: " + protocol);
    }

    @PostMapping("/targetconfig")
    TargetConfigEntity newTargetConfigEntity(@RequestBody TargetConfigEntity targetConfigEntity) {

        if (ObjectUtils.isEmpty(targetConfigEntity.getType())) {
            throw new TargetIDBadRequestException("Type should not be empty!");
        }
        if (ObjectUtils.isEmpty(targetConfigEntity.getProtocol())) {
            throw new TargetIDBadRequestException("Protocol should not be empty!");
        }
        if (ObjectUtils.isEmpty(targetConfigEntity.getPath())) {
            throw new TargetIDBadRequestException("Path should not be empty!");
        }
        if (ObjectUtils.isEmpty(targetConfigEntity.getUser())) {
            throw new TargetIDBadRequestException("User should not be empty!");
        }

        if (ObjectUtils.isEmpty(targetConfigEntity.getUser())) {
            throw new TargetIDBadRequestException("User should not be empty!");
        }

        if (ObjectUtils.isEmpty(targetConfigEntity.getPassword())) {
            throw new TargetIDBadRequestException("Password should not be empty!");
        }

        return repository.save(targetConfigEntity);
    }

    @PatchMapping("/targetconfig/{id}")
    public TargetConfigEntity patchTargetConfig(@PathVariable int id, @RequestBody TargetConfigEntity newTargetConfig) {
        TargetConfigEntity configToUpdate = repository.findById(id).orElseThrow(() -> new TargetIDNotFoundException("Could not find target config with ID: " + id));

        if(newTargetConfig.getType() != null)
        configToUpdate.setType(newTargetConfig.getType());

        if(newTargetConfig.getProtocol() != null)
            configToUpdate.setProtocol(newTargetConfig.getProtocol());

        if(newTargetConfig.getPort() != null)
            configToUpdate.setPort(newTargetConfig.getPort());

        if(newTargetConfig.getPath() != null)
            configToUpdate.setPath(newTargetConfig.getPort());

        if(newTargetConfig.getUser() != null)
            configToUpdate.setUser(newTargetConfig.getUser());

        if(newTargetConfig.getPassword() != null)
            configToUpdate.setPassword(newTargetConfig.getPassword());

        repository.save(configToUpdate);
        return configToUpdate;
    }























}
