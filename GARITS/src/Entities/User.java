/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daven
 */
public class User extends Item {

    public User() {
    }

    public enum UserType {
        ADMIN, RECEPTIONIST, FRANCHISEE, FOREPERSON,
        MECHANIC, NONE
    }

    public User(ResultSet rs) {
        try {
            setUsername(rs.getString("USERNAME"));
            setFirstName(rs.getString("FIRST_NAME"));
            setLastName(rs.getString("LAST_NAME"));
            setID(rs.getInt("USER_ID"));
            setType(rs.getInt("USER_TYPE"));
            setWallpaper(rs.getString("WALLPAPER"));
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setUsername(String username) {
        map.put("USERNAME", username);
    }

    public void setFirstName(String name) {
        map.put("FIRST_NAME", name);
    }

    public void setLastName(String name) {
        map.put("LAST_NAME", name);
    }

    public void setWallpaper(String wall) {
        map.put("WALLPAPER", wall);
    }

    public void setPassword(String password) {
        map.put("PASSWORD", password);
    }

    public void setID(int ID) {
        map.put("USER_ID", ID);
    }

    public void setType(int type) {
        map.put("USER_TYPE", type);
    }

    public UserType userType() {

        int type = userTypeInt();

        switch (type) {
            case 0:
                return UserType.ADMIN;
            case 1:
                return UserType.FRANCHISEE;
            case 2:
                return UserType.FOREPERSON;
            case 3:
                return UserType.MECHANIC;
            case 4:
                return UserType.RECEPTIONIST;
            default:
                return UserType.NONE;
        }
    }

    public int userTypeInt() {
        return (int) map.get("USER_TYPE");
    }

    public String username() {
        return (String) map.get("USERNAME");
    }

    public String firstName() {
        return (String) map.get("FIRST_NAME");
    }

    public String lastName() {
        return (String) map.get("LAST_NAME");
    }

    public String password() {
        return (String) map.get("PASSWORD");
    }

    public String wallpaper() {
        return (String) map.get("WALLPAPER");
    }

    public int ID() {
        return (int) map.get("USER_ID");
    }

    @Override
    public ItemType type() {
        return ItemType.USER;
    }

    @Override
    public String uniqueStr() {
        return "USERNAME=" + "'" + username() + "'";
    }

    @Override
    public Map<String, Object> map() {
        return map;
    }

    @Override
    public String IDStr() {
        return "USER_ID=" + ID();
    }
}
