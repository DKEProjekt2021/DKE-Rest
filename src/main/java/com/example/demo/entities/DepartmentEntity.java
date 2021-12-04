package com.example.demo.entities;

import javax.persistence.*;

//darstellung von department aus der db
@Entity
@Table(name = "department")
public class DepartmentEntity {
    @Id @GeneratedValue(strategy  = GenerationType.AUTO, generator = "departmentid_sequence")
    @SequenceGenerator(name = "departmentid_sequence",allocationSize=1)
    @Column(name = "department_id",nullable = false)
    private int departmentId;

    public DepartmentEntity(int departmentId, String name) {
        this.departmentId = departmentId;
        this.name = name;
    }

    public DepartmentEntity(){}

    @Column(name = "name",nullable = false)
    private String name;

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
