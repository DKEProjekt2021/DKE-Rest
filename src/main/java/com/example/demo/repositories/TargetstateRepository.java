package com.example.demo.repositories;

import com.example.demo.entities.EmployeeEntity;
import com.example.demo.entities.TargetstateEntity;
import com.example.demo.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


public interface TargetstateRepository extends JpaRepository<TargetstateEntity, Integer> {


    List<TargetstateEntity> findByActive(int active);

    List<TargetstateEntity> findByTargetconfigid(int targetconfigid);

    TargetstateEntity findByTargetstateid(int id);

    List<TargetstateEntity> findByActivesince(Date activeSince);

    List<TargetstateEntity> findByLastupdated(Date lastUpdated);

    List<TargetstateEntity> findByDepartmentid(int departmentid);

    @Transactional
    List removeByActive(int active);

    List<TargetstateEntity> findByType(String type);

    List<TargetstateEntity> findByPort(String port);

    List<TargetstateEntity> findByPath(String path);

    List<TargetstateEntity> findByUser(String user);

    List<TargetstateEntity> findByPassword(String password);

    List<TargetstateEntity> findByHostname(String hostname);

    List<TargetstateEntity> findByTablename(String tablename);

    List<TargetstateEntity> findByLastsynced(Timestamp lastsynced);
}
