/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabasePackage;

import Entities.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daven
 */

public class DBConnect {

    private static final DBConnect singleton = new DBConnect();
    private User user;
    public Connection con;
    
    /**
     * The singleton object of this class.
     * Only one instance of this class exists as there is no need
     * to create multiple instances. All classes can share this single instance.
     * @return the single instance of this class.
     */
    public static DBConnect sharedInstance() {
        return singleton;
    }

    /**
     * Returns the Connection object.
     * @return the Connection object.
     */
    public Connection con() {
        return con;
    }
    
    /**
     * Sets the global User after they have logged in.
     * This User will be accessible from all classes after they have successfully logged in.
     * @param user the User that has logged in, and is currently in session.
     * @see User
     */
    public void setUser(User user){
        this.user = user;
    }

    /**
     * Returns the currently-logged in User.
     * @return the currently-logged in User.
     * @see User
     */
    public User user(){
        return user;
    }
    
    private DBConnect() {

        System.out.println("Conneting...");
        String host = "jdbc:derby://localhost:1527/GARTIS";

        try {
            con = DriverManager.getConnection(host, "username", "password");

        } catch (SQLException err) {
            System.out.println("ERROR!" + err.getMessage());
        }
    }

    /**
     * Executes an 'UPDATE' statement on the database.
     * @param query the query to execute.
     */
    public void executeUpdateQuery(String query) {
        try {
            Statement stmt;
            stmt = con.createStatement();
            System.out.println("SQL STatement: \n" + query);
            stmt.executeUpdate(query);

        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Executes a 'SELECT' statement on the database.
     * This returns any rows as a ResultSet object.
     * @param query the query to execute.
     * @return the ResultSet of the query.
     * @see ResultSet
     */
    public ResultSet executeSelectQuery(String query) {
        try {
            Statement stmt;
            stmt = con.createStatement();
            System.out.println("SQL Select Query: \n" + query);
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Executes a 'DELETE' statement on the database.
     * Returns true if the query was successfully executed.
     * @param query the query to execute on the database.
     * @return whether the query was successfully executed.
     */
    public boolean executeDeleteQuery(String query) {
        try {
            Statement stmt;
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Backs up the database to a specified directory.
     * Saves a backup in the specified location with the current date appended at the end of the filename,
     * in the format 'yyyy-MM-dd'.
     * @param path the directory to save the backup to.
     * @throws SQLException an exception if there is an error when connecting to the database.
     */
    public static void backUpDatabase(String path) throws SQLException {
        java.text.SimpleDateFormat todaysDate
                = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String backupdirectory = path + "/"
                + todaysDate.format((java.util.Calendar.getInstance()).getTime());

        try (CallableStatement cs = DBConnect.sharedInstance().con().prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)")) {
            cs.setString(1, backupdirectory);
            cs.execute();
        }
        System.out.println("Backed up database to " + backupdirectory);
    }
}
