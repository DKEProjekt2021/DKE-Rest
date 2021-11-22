package com.example.demo.repositories;

import com.example.demo.entities.DepartmentEntity;
import com.example.demo.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Integer> {
    List<DepartmentEntity> findByName(String firstName);

    List<DepartmentEntity> findByDepartmentId(int departmentId);
}
