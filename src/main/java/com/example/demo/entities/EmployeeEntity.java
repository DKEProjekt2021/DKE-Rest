package com.example.demo.entities;

import javax.persistence.*;

import java.sql.Date;
import java.util.Objects;

//darstellung von einem employee aus der db
@Entity
@Table(name = "employee")
public class EmployeeEntity {


    private @Id @GeneratedValue
    int employeeid;

    @Column(name ="svnr")
    private  int svnr;
    @Column(name ="active")
    private int active;
    @Column(name ="first_name")
    private String firstName;
    @Column(name ="last_name")
    private String lastName;
    @Column(name ="login_name")
    private String login_name;
    @Column(name ="password")
    private String password;
    @Column(name ="start_date")
    private  Date start_date;
    @Column(name ="end_date")
    private Date end_date;
    @Column(name ="department")
    private int department;

    public EmployeeEntity() {
    }

    public EmployeeEntity(int svnr, String firstName, String lastName, int department) {
        this.svnr = svnr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.active = 1;
        this.login_name = String.valueOf(firstName.charAt(0)) + "_" + lastName + String.valueOf(Integer.toString(svnr).substring(0,4));
        this.password = String.valueOf(firstName.charAt(0)) + "_" + lastName + String.valueOf(Integer.toString(svnr).substring(0,4));
    }


    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public int getSVNR() {
        return svnr;
    }

    public void setSVNR(int SVNR) {
        this.svnr = SVNR;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public void generateStartingPassword() {
        this.password = String.valueOf(firstName.charAt(0)) + "_" + lastName + String.valueOf(employeeid);
    }
    public void generateLoginName() {
        this.login_name = String.valueOf(firstName.charAt(0)) + "_" + lastName + String.valueOf(employeeid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return svnr == that.svnr && active == that.active && firstName.equals(that.firstName) && lastName.equals(that.lastName) && start_date.equals(that.start_date) && end_date.equals(that.end_date) && department == (that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(svnr, active, firstName, lastName, start_date, end_date, department);
    }

    @Override
    public String toString() {
        return "EmployeeEntity{" +
                "SVNR=" + svnr +
                ", active=" + active +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", department='" + department + '\'' +
                '}';
    }
}