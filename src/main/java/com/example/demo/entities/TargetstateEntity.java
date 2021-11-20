package com.example.demo.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "targetconfig")
public class TargetstateEntity {

    private @Id @GeneratedValue
    int targetstateid;

    @Column(name ="active")
    private int active;

    @Column(name ="targetconfig_id")
    private int targetconfigid;

    @Column(name ="last_updated")
    private Date lastupdated;

    @Column(name ="active_since")
    private Date activesince;

    @Column(name ="department_id")
    private int departmentid;


    public TargetstateEntity() {

    }

    public int getTargetstateid() {
        return targetstateid;
    }

    public void setTargetstateid(int targetstateid) {
        this.targetstateid = targetstateid;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getTargetconfigid() {
        return targetconfigid;
    }

    public void setTargetconfigid(int targetconfigid) {
        this.targetconfigid = targetconfigid;
    }

    public Date getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Date lastupdated) {
        this.lastupdated = lastupdated;
    }

    public Date getActivesince() {
        return activesince;
    }

    public void setActivesince(Date activesince) {
        this.activesince = activesince;
    }

    public int getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }
}
