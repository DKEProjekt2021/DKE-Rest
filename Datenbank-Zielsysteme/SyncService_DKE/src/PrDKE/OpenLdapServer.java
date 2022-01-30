package PrDKE;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;

public class OpenLdapServer {

    DirContext connection;

    //Confiugarion for test methode (main)
    private final String hostname = "localhost";
    private final String port = "389";
    private final String authuser = "cn=Manager,dc=dkePr,dc=at";
    private final String authpassword = "secret";
    private final String context = "ou=People,dc=dkePr,dc=at";


    public static void main(String[] args) throws NamingException {     //Test methode for the LDAP connection.

        OpenLdapServer openLdapServer = new OpenLdapServer();
        openLdapServer.newConnection(openLdapServer.hostname, openLdapServer.port, openLdapServer.authuser, openLdapServer.authpassword);
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
        Employee testEmp = new Employee(99999, "lastTest", "loginTest", "passwordTest", sqlDate, "546457615");
        Map<String, Boolean> check = new HashMap<>();
        check.put("employeeid", true);
        check.put("last_name", true);
        check.put("first_name", true);
        check.put("login_name", true);
        check.put("password", true);
        check.put("start_date", true);
        check.put("end_date", true);
        check.put("svnr", true);

        openLdapServer.addUser(testEmp, check, openLdapServer.context);
        openLdapServer.getAllUsers(openLdapServer.context);
        //ldapServer.deleteUser(99999);
        // ldapServer.searchUsers(openLdapServer.context);
    }

    /* create connection during object creation */
    public void newConnection(String hostname, String port, String user, String password) {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://" + hostname + ":" + port);
        env.put(Context.SECURITY_PRINCIPAL, user);
        env.put(Context.SECURITY_CREDENTIALS, password);
        try {
            connection = new InitialDirContext(env);
        } catch (AuthenticationException ex) {
            System.out.println(ex.getMessage());
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getAllUsers(String searchName) throws NamingException {
        String searchFilter = "(objectClass=inetOrgPerson)";
        String[] reqAtt = {"cn", "sn"};
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(reqAtt);

        NamingEnumeration users = connection.search(searchName, searchFilter, controls);

        SearchResult result;
        while (users.hasMore()) {
            result = (SearchResult) users.next();
            Attributes attr = result.getAttributes();
            //String name = attr.get("cn").get(0).toString();
            //deleteUserFromGroup(name,"Administrators");
            System.out.print(attr.get("cn") + "\t");
            System.out.println(attr.get("sn"));
        }

    }

    public void addUser(Employee employee, Map<String, Boolean> check, String context) {

        String description = "";

        Attributes attributes = new BasicAttributes();
        Attribute attribute = new BasicAttribute("objectClass");
        attribute.add("inetOrgPerson");
        attribute.add("OpenLDAPperson");
        attribute.add("organizationalPerson");
        attribute.add("person");
        attribute.add("pilotPerson");

        attributes.put(attribute);

        // user details can be added here.
        try {
            if (check.get("first_name")) {
                attributes.put("cn", employee.getFirstName());
            } else attributes.put("cn", "empty");
            if (check.get("last_name")) {
                attributes.put("sn", employee.getLastName());
            } else attributes.put("cn", "empty");

//  Attributes do not have any perfectly correspondending attribute LDAP Server
            //Only input those which are checked in the targetconfig

            if (check.get("svnr")) {
                attributes.put("employeeNumber", employee.getSvnr());
            }
            if (check.get("password")) {
                attributes.put("userPassword", employee.getUserPassword());
            }
            if (check.get("login_name")) {
                attributes.put("givenName", employee.getLoginName());
            }
            if (check.get("start_date") && employee.getStartDate() != null) {
                description += "-StartDate: " + employee.getStartDate().toString();
            }
            if (check.get("end_date") && employee.getEndDate() != null) {
                description += "-EndDate: " + employee.getEndDate().toString();
            }

            attributes.put("description", description);

            attributes.put("uid", String.valueOf(employee.getUid()));
            try {
                connection.createSubcontext("uid=" + employee.getUid() + context, attributes);
            } catch (NamingException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Error extracting the data. Has everything been handed over that is also specified in the check list?");
        }
    }

    public void deleteUser(int uid, String context) {
        try {
            connection.destroySubcontext("uid=" + uid + context);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }


    //More Methods for LDAP integration not used in the current configuration

    public void addUserToGroup(String username, String groupName) {
        ModificationItem[] mods = new ModificationItem[1];
        Attribute attribute = new BasicAttribute("uniqueMember", "cn=" + username + ",ou=users,ou=system");
        mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attribute);
        try {
            connection.modifyAttributes("cn=" + groupName + ",ou=groups,ou=system", mods);
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }


    public void deleteUserFromGroup(String username, String groupName, String context) {    //context could be ",ou=users,ou=system"
        ModificationItem[] mods = new ModificationItem[1];
        Attribute attribute = new BasicAttribute("uniqueMember", "cn=" + username + ",ou=users,ou=system");
        mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attribute);
        try {
            connection.modifyAttributes("cn=" + groupName + context, mods);
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

    public void searchUsers(String context) throws NamingException {    //context could be ",ou=users,ou=system"
        //String searchFilter = "(uid=1)"; //  for one user
        //String searchFilter = "(&(uid=1)(cn=Smith))"; // and condition
        String searchFilter = "(|(uid=1)(uid=2)(cn=Smith))"; // or condition
        String[] reqAtt = {"cn", "sn", "uid"};
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(reqAtt);

        NamingEnumeration users = connection.search(context, searchFilter, controls);

        SearchResult result;
        while (users.hasMore()) {
            result = (SearchResult) users.next();
            Attributes attr = result.getAttributes();
            //String name = attr.get("cn").get(0).toString();
            //deleteUserFromGroup(name,"Administrators");
            System.out.println(attr.get("cn"));
            System.out.println(attr.get("sn"));
            System.out.println(attr.get("uid"));
        }
    }


    public static boolean authUser(String username, String password, String context) { //context could be ",ou=users,ou=system"
        try {
            Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://localhost:10389");
            env.put(Context.SECURITY_PRINCIPAL, "cn=" + username + context);  //check the DN correctly
            env.put(Context.SECURITY_CREDENTIALS, password);
            DirContext con = new InitialDirContext(env);
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("failed: " + e.getMessage());
            return false;
        }
    }

    public void updateUserPassword(String username, String password, String context) { //context could be ",ou=users,ou=system" for users in LDAP
        try {
            ModificationItem[] mods = new ModificationItem[1];
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", password));
            connection.modifyAttributes("cn=" + username + context, mods);
        } catch (Exception e) {
            System.out.println("failed: " + e.getMessage());
        }
    }
}