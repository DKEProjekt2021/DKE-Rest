package com.example.demo.repositories;

import com.example.demo.entities.EmployeeEntity;
import com.example.demo.entities.TargetConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TargetConfigRepository extends JpaRepository<TargetConfigEntity, Integer> {

    List<TargetConfigEntity> findByType(String type);
    List<TargetConfigEntity> findByProtocol(String protocol);

    @Transactional
    List<TargetConfigEntity> removeByType(String type);
    @Transactional
    List<TargetConfigEntity> removeByProtocol(String protocol);

}
