package PrDKE;

import java.sql.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Synchronize_DKE {

    //Source Database configuration file (CentralDB)

    public static final String USER = "DKETeam";
    public static final String PWD = "mydkepassword";
    public static final String CONNECT_STRING = "jdbc:mysql://dkeprw21.mysql.database.azure.com:3306/central_db_t3";

    public static final boolean ldapConfigured = true;


    public static void main(String[] args) {
        execute();
    }

    public static void execute() {

        try (Connection con = DriverManager.getConnection(CONNECT_STRING, USER, PWD)) {


            while (true) {      //Syncing every 5min for demonstration purpose
                sync(con);
                System.out.println("Successfully Synced at: " + Instant.now());
                sleep(300000);
            }

        } catch (
                SQLException | InterruptedException ex) { // catch exceptions connecting to database
            System.err.println("SQL Exception outside transaction");
            ex.printStackTrace(System.err);
        }
    }

    public static void sync(Connection con) {
        //default no information is synced
        String query = "SELECT * From targetstate WHERE active='1' AND (type='LDAP' OR type='MYSQL')";
        Map<String, Boolean> check = new HashMap<>();
        check.put("employeeid", false);
        check.put("last_name", false);
        check.put("first_name", false);
        check.put("login_name", false);
        check.put("password", false);
        check.put("start_date", false);
        check.put("end_date", false);
        check.put("svnr", false);


        try (PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                System.out.println("Working on ID: " + result.getString("targetstate_id"));
                //get Connection details
                String hostname = result.getString("hostname");
                String port = result.getString("port");
                String path = result.getString("path");
                String user = result.getString("user");
                String password = result.getString("password");
                String tablename = result.getString("tablename");

                //create Connection to LDAP
                int depID = result.getInt("department_id");
                int targetstateId = result.getInt("targetstate_id");
                Timestamp lastSync = result.getTimestamp("last_synced");

                query = "SELECT * From targetconfig WHERE targetconfig_id=?";
                try (PreparedStatement stmt2 = con.prepareStatement(query)) {
                    stmt2.setInt(1, result.getInt("targetconfig_id"));
                    ResultSet resultConf = stmt2.executeQuery();
                    resultConf.next();

                    //Set the wanted information checks (which information fields should be synced)

                    for (Map.Entry<String, Boolean> entry : check.entrySet()) {
                        if (resultConf.getInt("check_" + entry.getKey()) == 1)
                            entry.setValue(true);
                        else
                            entry.setValue(false);
                    }

                    //Select all employees in the given department
                    query = "SELECT * From employee WHERE department_id=?";
                    try (PreparedStatement stmt3 = con.prepareStatement(query)) {
                        stmt3.setInt(1, depID);
                        ResultSet resultEmp = stmt3.executeQuery();
                        Employee emp;
                        while (resultEmp.next()) {

                            Timestamp lastChanged = resultEmp.getTimestamp("last_changed");

                            // System.out.println(lastChanged + " last Sync: " + lastSync);

                            //only sync if employee changed afert lastSync date of Targetstate
                            if (lastSync == null || lastChanged.after(lastSync)) {
                                String lastName = resultEmp.getString("last_name");
                                String firstName = resultEmp.getString("first_name");
                                if (resultEmp.wasNull()) {
                                    firstName = "-";
                                }
                                String loginName = resultEmp.getString("login_name");
                                String userPassword = resultEmp.getString("password");
                                Date startDate = resultEmp.getDate("start_date");
                                Date endDate = resultEmp.getDate("end_date");
                                if (resultEmp.wasNull()) {
                                    endDate = null;
                                }
                                String svnr = resultEmp.getString("svnr");
                                int uid = resultEmp.getInt("employeeid");

                                emp = new Employee(uid, firstName, lastName, loginName, userPassword, startDate, endDate, svnr);

                                if (result.getString("type").equals("LDAP") && ldapConfigured) {    //only Sync Ldap if configured
                                    System.out.println("export to LDAP Lastname:" + lastName);
                                    syncLdap(hostname, port, path, user, password, resultEmp.getInt("active") == 1, check, emp);    // insert only if active is true (1)
                                } else {
                                    System.out.println("export to MySQL Lastname:" + lastName);
                                    syncMysql(hostname, port, path, tablename, user, password, resultEmp.getInt("active") == 1, check, emp);    // insert only if active is true (1)
                                }

                            }
                        }
                    }
                }

                // Update the lastSynced date of the targetstate
                query = "UPDATE targetstate SET last_synced=? WHERE targetstate_id=?";
                try (PreparedStatement stmt4 = con.prepareStatement(query)) {
                    stmt4.setTimestamp(1, Timestamp.from(Instant.now()));
                    stmt4.setInt(2, targetstateId);

                    stmt4.executeUpdate();
                    //   con.commit();
                }

            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception during read transaction - report and ignore - no rollback necessary");
            ex.printStackTrace(System.err);
        }
    }

    public static void syncLdap(String dbHostname, String dbPort, String dbPath, String dbUser, String dbPassword, boolean insert, Map<String, Boolean> check,
                                Employee employee) {
        OpenLdapServer openLdapServer = new OpenLdapServer();
        openLdapServer.newConnection(dbHostname, dbPort, dbUser, dbPassword);

        openLdapServer.deleteUser(employee.getUid(), dbPath);
        if (insert) {
            openLdapServer.addUser(employee, check, dbPath);
        }
    }

    public static void syncMysql(String dbHostname, String dbPort, String dbPath, String tablename, String dbUser, String dbPassword, boolean insert, Map<String, Boolean> check
            , Employee employee) {

        try (Connection con = DriverManager.getConnection("jdbc:mysql://" + dbHostname + ":" + dbPort + dbPath, dbUser, dbPassword)) {

            //build dynamic sql statement
            if (insert) {

                String build = "REPLACE " + tablename + " (";
                String buildVal = "VALUES (";
                for (Map.Entry<String, Boolean> entry : check.entrySet()
                ) {
                    if (entry.getValue()) {
                        build += entry.getKey() + ",";
                        buildVal += "?,";
                    }
                }

                build = build.substring(0, build.length() - 1) + ") ";
                buildVal = buildVal.substring(0, buildVal.length() - 1) + ")";
                String query = build + buildVal;
                //System.out.println(query);

                // String query = "REPLACE " + tablename + " (employeeid,department_id,first_name,last_name)  VALUES (?,?,?,?)";
                //query = "REPLACE targetemployee (employeeid,first_name,last_name) VALUES (45,testname,testlast)";
                try (PreparedStatement stmt = con.prepareStatement(query)) {
                    int i = 0;
                    if (check.get("end_date")) {
                        i++;
                        stmt.setDate(i, employee.getEndDate());
                    }
                    if (check.get("svnr")) {
                        i++;
                        stmt.setString(i, employee.getSvnr());
                    }
                    if (check.get("password")) {
                        i++;
                        stmt.setString(i, employee.getUserPassword());
                    }
                    if (check.get("login_name")) {
                        i++;
                        stmt.setString(i, employee.getLoginName());
                    }
                    if (check.get("last_name")) {
                        i++;
                        stmt.setString(i, employee.getLastName());
                    }
                    if (check.get("employeeid")) {
                        i++;
                        stmt.setInt(i, employee.getUid());
                    }
                    if (check.get("first_name")) {
                        i++;
                        stmt.setString(i, employee.getFirstName());
                    }
                    if (check.get("start_date")) {
                        i++;
                        stmt.setDate(i, employee.getStartDate());
                    }

                    stmt.executeUpdate();

                    // con.commit();
                }
            } else {
                String query = "DELETE FROM " + tablename + " WHERE employeeid=?";

                try (PreparedStatement stmt = con.prepareStatement(query)) {
                    stmt.setInt(1, employee.getUid());
                    stmt.executeUpdate();
                    // con.commit();
                }
            }

        } catch (SQLException ex) {
            System.err.println("SQL EXCEPTION");
            ex.printStackTrace(System.err);
        }
    }


}

