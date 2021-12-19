package com.example.demo.repositories;

import com.example.demo.entities.EmployeeEntity;
import com.example.demo.entities.TargetConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TargetConfigRepository extends JpaRepository<TargetConfigEntity, Integer> {



}
