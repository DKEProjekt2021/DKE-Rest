package com.example.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "targetconfig")
public class TargetConfigEntity {

    private @Id @GeneratedValue(strategy  = GenerationType.AUTO, generator = "targetid_sequence")
    @SequenceGenerator(name = "targetid_sequence",allocationSize=1)
    int targetconfigid;

    @Column(name ="check_employeeid")
    private int checkEmployeeId;

    @Column(name ="check_last_name")
    private int checkLastName;

    @Column(name ="check_first_name")
    private int checkFirstName;

    @Column(name ="check_login_name")
    private int checkLoginName;

    @Column(name ="check_password")
    private int check_password;

    @Column(name ="check_start_date")
    private int checkStartDate;

    @Column(name ="check_end_date")
    private int checkEndDate;

    @Column(name ="check_SVNR")
    private int checkSVNR;


    public TargetConfigEntity() {

    }

    public int getTargetconfig_id() {
        return targetconfigid;
    }

    public void setTargetconfig_id(int targetconfig_id) {
        this.targetconfigid = targetconfig_id;
    }

    public int getCheckEmployeeId() {
        return checkEmployeeId;
    }

    public void setCheckEmployeeId(int checkEmployeeId) {
        this.checkEmployeeId = checkEmployeeId;
    }



    public int getCheckLastName() {
        return checkLastName;
    }

    public void setCheckLastName(int checkLastName) {
        this.checkLastName = checkLastName;
    }

    public int getCheckFirstName() {
        return checkFirstName;
    }

    public void setCheckFirstName(int checkFirstName) {
        this.checkFirstName = checkFirstName;
    }

    public int getCheckLoginName() {
        return checkLoginName;
    }

    public void setCheckLoginName(int checkLoginName) {
        this.checkLoginName = checkLoginName;
    }

    public int getCheck_password() {
        return check_password;
    }

    public void setCheck_password(int check_password) {
        this.check_password = check_password;
    }

    public int getCheckStartDate() {
        return checkStartDate;
    }

    public void setCheckStartDate(int checkStartDate) {
        this.checkStartDate = checkStartDate;
    }

    public int getCheckEndDate() {
        return checkEndDate;
    }

    public void setCheckEndDate(int checkEndDate) {
        this.checkEndDate = checkEndDate;
    }


    public int getCheckSVNR() {
        return checkSVNR;
    }

    public void setCheckSVNR(int checkSVNR) {
        this.checkSVNR = checkSVNR;
    }
}
