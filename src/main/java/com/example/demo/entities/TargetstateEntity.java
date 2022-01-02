package com.example.demo.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "targetstate")
public class TargetstateEntity {

    private @Id @GeneratedValue(strategy  = GenerationType.AUTO, generator = "targetstateid_sequence")
    @SequenceGenerator(name = "targetstateid_sequence",allocationSize=1)
    @Column(name ="targetstate_id")
    int targetstateid;

    @Column(name ="active")
    private int active;

    @Column(name ="type")
    private String type;

    @Column(name = "targetconfig_id")
    private int targetconfigid;

    @Column(name ="last_updated")
    private Date lastupdated;

    @Column(name ="active_since")
    private Date activesince;

    @JoinColumn(name = "department_id")
    @Column(name ="department_id")
    private int departmentid;

    @Column(name ="port")
    private String port;

    @Column(name ="path")
    private String path;

    @Column(name ="user")
    private String user;

    @Column(name ="password")
    private String password;

    @Column(name ="hostname")
    private String hostname;

    @Column(name ="tablename")
    private String tablename;

    @Column(name ="last_synced")
    private Timestamp lastsynced;


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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public Timestamp getLast_synced() {
        return lastsynced;
    }

    public void setLast_synced(Timestamp lastsynced) {
        this.lastsynced = lastsynced;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
