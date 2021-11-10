package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//darstellung von department aus der db
@Entity
@Table(name = "department")
public class DepartmentEntity {
    @Id
    @Column(name = "department_id",nullable = false)
    private String departmentId;

    @Column(name = "name",nullable = false)
    private String name;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
