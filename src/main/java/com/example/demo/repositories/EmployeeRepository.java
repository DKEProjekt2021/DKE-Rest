package com.example.demo.repositories;

import com.example.demo.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    List<EmployeeEntity> findByFirstName(String firstName);
    List<EmployeeEntity> findByLastName(String lastName);
    List<EmployeeEntity> findByActive(int active);
    List<EmployeeEntity> findByDepartmentId(int department_Id);
    List<EmployeeEntity> findByLoginName(String login_name);

    @Transactional
    List<EmployeeEntity> removeByActive(int active);

}
