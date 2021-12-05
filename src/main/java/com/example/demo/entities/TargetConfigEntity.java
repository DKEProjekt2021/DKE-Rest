package com.example.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "targetconfig")
public class TargetConfigEntity {

    private @Id @GeneratedValue(strategy  = GenerationType.AUTO, generator = "targetid_sequence")
    @SequenceGenerator(name = "targetid_sequence",allocationSize=1)
    int targetconfig_id;

    @Column(name ="type")
    private String type;

    @Column(name ="protocol")
    private String protocol;

    @Column(name ="port")
    private String port;

    @Column(name ="path")
    private String path;

    @Column(name ="user")
    private String user;

    @Column(name ="password")
    private String password;

    @Lob
    @Column(name = "ssl_certificate")
    private byte[] blob;

    public TargetConfigEntity() {

    }


    public int getTargetconfig_id() {
        return targetconfig_id;
    }

    public void setTargetconfig_id(int targetconfig_id) {
        this.targetconfig_id = targetconfig_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
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

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }
}
