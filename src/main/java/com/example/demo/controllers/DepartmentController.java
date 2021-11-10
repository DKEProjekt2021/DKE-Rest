package com.example.demo.controllers;

import com.example.demo.entities.DepartmentEntity;
import com.example.demo.entities.EmployeeEntity;
import com.example.demo.exceptions.DepartmentBadRequestException;
import com.example.demo.exceptions.EmployeeRequestNotFoundException;
import com.example.demo.repositories.DepartmentRepository;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class DepartmentController {


    private final DepartmentRepository repository;

    public DepartmentController(DepartmentRepository repository) {
        this.repository = repository;
    }

    //gibt alle employees zurück
    @GetMapping("/departments")
    List<DepartmentEntity> all() {
        return repository.findAll();
    }

    //Getter für DepartmentName
    @GetMapping("departments/name/{name}")
    List <DepartmentEntity> withDepartmentName(@PathVariable String firstName) {
        return repository.findByName(firstName);
    }
    //Getter für ID
    @GetMapping("departments/{departmentId}")
    List <EmployeeEntity> areActive(@PathVariable int departmentId) {
        return repository.findByDepartmentId(departmentId);
    }

    @PostMapping("/employees")
    DepartmentEntity newDepartmentEntity(@RequestBody DepartmentEntity departmentEntity) {
        if(ObjectUtils.isEmpty(departmentEntity.getName())) {
            throw new DepartmentBadRequestException("Department-name should not be empty!");
        }
        if(!departmentEntity.getName().matches ("\\w+\\.?")) {
            throw new DepartmentBadRequestException("First name in-correctly formatted!");
        }
        return repository.save(departmentEntity);
    }

    //PatchMapping = Updaten oder Put Mapping(komplett ersetzen jedes Feld wird neu angelegt)
    @PatchMapping("/employee/{id}")
    public DepartmentEntity patchDepartment(@PathVariable int id, @RequestBody DepartmentEntity newEmployeeData) {
        DepartmentEntity emp = repository.findById(id).orElseThrow(() -> new EmployeeRequestNotFoundException(id));

        if(newEmployeeData.getName() != null) {
            emp.setName(newEmployeeData.getName());
        }
        repository.save(emp);
        return emp;
    }


}
