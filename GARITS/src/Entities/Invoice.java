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
public class Invoice extends Item{
    
    public Invoice(ResultSet rs){
        try {
            setDueDate(rs.getDate("DUE_DATE"));
                    setID(rs.getInt("ID"));
                    setJobID(rs.getInt("JOB_ID"));
                    setAmount(rs.getDouble("AMOUNT"));
        } catch (SQLException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Invoice(){
        
    }
    
    // Getters
    
    public int ID(){
                return (int)map.get("ID");
    }
    
    public double amount(){
        return (double)map.get("AMOUNT");
    }
    
    public java.sql.Date dueDate(){
        return (java.sql.Date)map.get("DUE_DATE");
    }
    
    public int jobID(){
            return (int)map.get("JOB_ID");
    }
    
    public Job job(){
        return (Job)map.get("JOB");
    }
    
    // Setters
    
    public void setID(int ID){
        map.put("ID", ID);
    }
    
    public void setAmount(double amount){
        map.put("AMOUNT", amount);
    }
    
    public void setDueDate(java.sql.Date date) {
        map.put("DUE_DATE", date);
    }
    
    public void setJobID(int jobID){
        map.put("JOB_ID", jobID);
    }
    
        public void setJob(Job job){
        setJobID(job.ID());
        map.put("JOB", job);
    }

    @Override
    public ItemType type() {
        return ItemType.INVOICE;
    }

    @Override
    public String uniqueStr() {
        return "JOB_ID=" + jobID();
    }
    
    @Override
    public String IDStr() {
        return uniqueStr();
    }
    
    @Override
        public Map<String, Object> map(){
        return map;
    }
}