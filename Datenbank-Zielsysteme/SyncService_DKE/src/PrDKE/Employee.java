package PrDKE;

import java.sql.Date;

public class Employee {

    private String firstName;
    private String lastName;
    private String loginName;
    private String userPassword;
    private Date startDate;
    private Date endDate;
    private String svnr;
    private int uid;

    public Employee(int uid, String firstName, String lastName, String loginName, String userPassword, Date startDate, Date endDate, String svnr) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.loginName = loginName;
        this.userPassword = userPassword;
        this.startDate = startDate;
        this.endDate = endDate;
        this.svnr = svnr;
    }

    public Employee(int uid, String lastName, String loginName, String userPassword, Date startDate, String svnr) {
        this.uid = uid;
        this.lastName = lastName;
        this.loginName = loginName;
        this.userPassword = userPassword;
        this.startDate = startDate;
        this.svnr = svnr;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSvnr() {
        return svnr;
    }

    public void setSvnr(String svnr) {
        this.svnr = svnr;
    }


}
