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
public class Task extends Item {

    public Task(ResultSet rs) {
        System.out.println("Task Constructor - rs");
        initWithResultSet(rs);
    }

    public Task(ResultSet rs, int customDur) {
        System.out.println("Task Constructor - rs, custom");
        initWithResultSet(rs);
        setDuration(customDur);
    }

    public Task() {
        System.out.println("Task Constructor -");
    }

    public void initWithResultSet(ResultSet rs) {
        try {

            setID(rs.getInt("TASK_ID"));
            setName(rs.getString("TASK_NAME"));
            setDescription(rs.getString("TASK_DESCRIPTION"));
            setDuration(rs.getInt("TASK_DURATION"));

        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setID(int ID) {
        map.put("TASK_ID", ID);
    }

    public void setName(String name) {
        map.put("TASK_NAME", name);
    }

    public void setDescription(String desc) {
        map.put("TASK_DESCRIPTION", desc);
    }

    public void setDuration(int minutes) {
        map.put("TASK_DURATION", minutes);
    }

    public int ID() {
        return (int) map.get("TASK_ID");
    }

    public String name() {
        return (String) map.get("TASK_NAME");
    }

    public String description() {
        return (String) map.get("TASK_DESCRIPTION");
    }

    public int duration() {
        return (int) map.get("TASK_DURATION");
    }

    @Override
    public ItemType type() {
        return ItemType.TASK;
    }

    @Override
    public String uniqueStr() {
        return "TASK_NAME='" + name() + "'";
    }

    @Override
    public String IDStr() {
        return "TASK_ID=" + ID();
    }

    @Override
    public Map<String, Object> map() {
        return map;
    }
}
