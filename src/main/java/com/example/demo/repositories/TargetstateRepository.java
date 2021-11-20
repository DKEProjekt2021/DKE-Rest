package com.example.demo.repositories;

import com.example.demo.entities.EmployeeEntity;
import com.example.demo.entities.TargetstateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;


public interface TargetstateRepository extends JpaRepository<TargetstateEntity, Integer> {


    List<TargetstateEntity> findByActive(int active);

    TargetstateEntity findByTargetconfigid(int targetconfigid);

    TargetstateEntity findByTargetstateid(int id);

    List<TargetstateEntity> findByActivesince(Date activeSince);

    List<TargetstateEntity> findByLastupdated(Date lastUpdated);

    List<TargetstateEntity> findByDepartmentid(int departmentid);

    @Transactional
    List removeByActive(int active);
}
