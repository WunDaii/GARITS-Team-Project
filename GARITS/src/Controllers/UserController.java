/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.User;
import DatabasePackage.DBConnect;
import Entities.Mechanic;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author Daven
 */
public class UserController  extends BaseController {
    
    /**
     * Returns whether a User already exists.
     * Checks whether the User's username already exists in the database.
     * @param user the User to check in the database.
     * @return whether the User's username already exists in the database.
     * @throws SQLException the exception that describes an error when accessing the database.
     * @see User
     */
    public boolean countUser(User user) throws SQLException{
                String SQL = "SELECT *";
        String from = "FROM Users";
        String where = "WHERE username='" + user.username() + "'";
        String query = SQL + " " + from + " " + where;

        ResultSet rs = DBConnect.sharedInstance().executeSelectQuery(query);

        int rowNum = 0;
        while (rs.next()) {
            return true;
        }

        return false;

    }

    /**
     * Logs in the User.
     * Returns true if the username and password exist in the database, or false if they do not.
     * @param user the User to log into the database.
     * @return whether the User has logged in successfully.
     * @throws SQLException the exception that describes an error when accessing the database.
     * @see User
     */
    public boolean loginUser(User user) throws SQLException {
        String SQL = "SELECT *";
        String from = "FROM Users";
        String where = "WHERE username='" + user.username() + "' AND password='" + user.password() + "'";
        String query = SQL + " " + from + " " + where;

        ResultSet rs = DBConnect.sharedInstance().executeSelectQuery(query);

        int rowNum = 0;
        while (rs.next()) {
            
                                    if (rs.getInt("USER_TYPE") == 3){
                            user = new Mechanic(rs);
                        }else{
                        user = new User(rs);
                        }
            DBConnect.sharedInstance().setUser(user);
            
            return true;
        }

        return false;

    }
}
