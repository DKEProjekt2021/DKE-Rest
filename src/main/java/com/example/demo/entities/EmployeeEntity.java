package com.example.demo.entities;

import javax.persistence.*;

import java.sql.Date;
import java.time.Instant;
import java.util.Objects;


//Java Objekt Darstellung der Employee Tabelle
@Entity
@Table(name = "employee")
public class EmployeeEntity {

    //id wird über einen generator automatisch generiert und ist eine fortlaufende nummer
    private @Id @GeneratedValue(strategy  = GenerationType.AUTO, generator = "employeeid_sequence")
    @SequenceGenerator(name = "employeeid_sequence",allocationSize=1)
    int employeeid;

    @Column(name ="svnr")
    private  String svnr;
    @Column(name ="active")
    private int active;
    @Column(name ="first_name")
    private String firstName;
    @Column(name ="last_name")
    private String lastName;
    @Column(name ="login_name")
    private String loginName;
    @Column(name ="password")
    private String password;
    @Column(name ="start_date")
    private  Date start_date;
    @Column(name ="end_date")
    private Date end_date;
    @Column(name ="department_id")
    private int departmentId;
    @Column(name ="last_changed")
    private Instant lastChanged;


    public EmployeeEntity() {
    }

    public EmployeeEntity(String svnr, String firstName, String lastName, int departmentId) {
        this.svnr = svnr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentId = departmentId;
        this.active = 1;
        this.loginName = String.valueOf(firstName.charAt(0)) + "_" + lastName + svnr.substring(0,4);
        this.password = String.valueOf(firstName.charAt(0)) + "_" + lastName + svnr.substring(0,4);
        this.lastChanged = Instant.now();
    }


    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public String getSVNR() {
        return svnr;
    }

    public void setSVNR(String SVNR) {
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
        return loginName;
    }

    public void setLogin_name(String login_name) {
        this.loginName = login_name;
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

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public void generateStartingPassword(String id) {
        this.password = String.valueOf(firstName.charAt(0)) + "_" + lastName + id;
    }
    public void generateLoginName(int employeeid) {
        this.loginName = String.valueOf(firstName.charAt(0)) + "_" + lastName + String.valueOf(employeeid);
    }

    public Instant getLastChanged() {
        return lastChanged;
    }

    public void setLastChanged(Instant lastChanged) {
        this.lastChanged = lastChanged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return svnr == that.svnr && active == that.active && firstName.equals(that.firstName) && lastName.equals(that.lastName) && start_date.equals(that.start_date) && end_date.equals(that.end_date) && departmentId == (that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(svnr, active, firstName, lastName, start_date, end_date, departmentId);
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
                ", department='" + departmentId + '\'' +
                '}';
    }
}