/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daven
 */
public class JobType extends Item {

    public JobType(ResultSet rs) {
        try {
            setID(rs.getInt("JOB_TYPE_ID"));
            setName(rs.getString("JOB_TYPE_NAME"));
        } catch (SQLException ex) {
            Logger.getLogger(JobType.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JobType() {
    }

    public void setID(int ID) {
        map.put("JOB_TYPE_ID", ID);
    }

    public void setName(String name) {
        map.put("JOB_TYPE_NAME", name);
    }

    public int ID() {
        return (int) map.get("JOB_TYPE_ID");
    }

    public String name() {
        return (String) map.get("JOB_TYPE_NAME");
    }

    @Override
    public ItemType type() {
        return ItemType.JOB_TYPE;
    }

    @Override
    public String IDStr() {
        return "JOB_TYPE_ID=" + ID();
    }

    @Override
    public String uniqueStr() {
        return "JOB_TYPE_NAME='" + name() + "'";
    }
}
