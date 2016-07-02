/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daven
 */
public class Job extends Item {

    public enum JobStatusType {
        PENDING, IN_PROGRESS, COMPLETED, NONE
    }

    public Job(ResultSet rs) {
        try {
            
            map.put("JOB_ID", rs.getInt("JOB_ID"));
            map.put("VEHICLE_REG", rs.getString("VEHICLE_REG"));
            map.put("DATE", rs.getDate("DATE"));
            map.put("CUSTOMER_ID", rs.getInt("CUSTOMER_ID"));
            map.put("EST_TIME", rs.getInt("EST_TIME"));
            map.put("ACTUAL_TIME", rs.getInt("ACTUAL_TIME"));
            map.put("WORK_REQUIRED", rs.getString("WORK_REQUIRED"));
            map.put("WORK_CARRIED", rs.getString("WORK_CARRIED"));
            map.put("COST", rs.getDouble("COST"));
            map.put("ITEM_IDS", rs.getString("ITEM_IDS"));
            map.put("MECHANIC_IDS", rs.getString("MECHANIC_IDS"));
            map.put("STATUS", rs.getInt("STATUS"));
            map.put("TASK_IDS", rs.getString("TASK_IDS"));
            map.put("PAID", rs.getBoolean("PAID"));
            
            Customer customer = new Customer(rs);
            Vehicle vehicle = new Vehicle(rs);
            
            setVehicle(vehicle);
                        this.setCustomer(customer);
                        setJobType(new JobType(rs));
                        
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Job() {

    }

    // SETTERS
    public void setID(int ID) {
        map.put("JOB_ID", ID);
    }

    public void setVehicleReg(String vehicleReg) {
        map.put("VEHICLE_REG", vehicleReg);
    }
    
    public void setVehicle(Vehicle vehicle){
        map.put("VEHICLE", vehicle);
        setVehicleReg(vehicle.regNo());
    }

    public void setCustomerID(int customerID) {
        map.put("CUSTOMER_ID", customerID);
    }

    public void setCustomer(Customer customer) {
        setCustomerID(customer.ID());
        map.put("CUSTOMER", customer);
    }

    public void setEstTime(int estTime) {
        map.put("EST_TIME", estTime);
    }

    public void setActualTime(int actualTime) {
        map.put("EST_TIME", actualTime);
    }

    public void setWorkRequired(String workRequired) {
        map.put("WORK_REQUIRED", workRequired);
    }

    public void setWorkCarried(String workCarried) {
        map.put("WORK_CARRIED", workCarried);
    }

    public void setDate(java.sql.Date date) {
        map.put("DATE", date);
    }

    public void setCost(double cost) {
        map.put("COST", cost);
    }

    public void setStatus(int status) {
        map.put("STATUS", status);
    }

    public void setMechanicIDS(String mechIDs) {
        map.put("MECHANIC_IDS", mechIDs);
    }

    public void setMechanics(ArrayList<Mechanic> mechanics) {
        map.put("MECHANICS", mechanics);

                ArrayList<String> strings = new ArrayList<>();

        for (Mechanic mech : mechanics) {
            strings.add(mech.ID() + "=" + mech.minsWorked());
        }

        String finalStr = String.join(",", strings);
                setMechanicIDS(finalStr);
    }

    public void setItemIDs(String itemIDs) {
        map.put("ITEM_IDS", itemIDs);
    }

    public void setItems(List<JobStock> items) {
        map.put("ITEMS", items);

        ArrayList<String> strings = new ArrayList<>();

        for (JobStock stock : items) {
            strings.add(stock.itemNumber()+ "=" + stock.jobQuantity());
        }

        String finalStr = String.join(",", strings);
        setItemIDs(finalStr);
    }

    public void setTaskIDs(String taskIDs) {
        map.put("TASK_IDS", taskIDs);
    }

    public void setTasks(ArrayList<Task> tasks) {
        map.put("TASKS", tasks);

        ArrayList<String> strings = new ArrayList<>();

        for (Task task : tasks) {
            strings.add("" + task.ID() + "=" + task.duration());
        }

        String finalStr = String.join(",", strings);
        setTaskIDs(finalStr);
    }
    
    public void setJobType(JobType type){
        setJobTypeID(type.ID());
        map.put("JOB_TYPE", type);
    }
    
    public void setJobTypeID(int typeID){
        map.put("JOB_TYPE_ID", typeID);
    }
    
    public void setPaid(Boolean paid){
        map.put("PAID", paid);
    }

    // GETTERS
    public int ID() {
        return (int) map.get("JOB_ID");
    }

    public String vehicleReg() {
        return (String) map.get("VEHICLE_REG");
    }
    
    public Vehicle vehicle(){
        return (Vehicle)map.get("VEHICLE");
    }

    public int customerID() {
        return (int) map.get("CUSTOMER_ID");
    }

    public Customer customer() {
        return (Customer) map.get("CUSTOMER");
    }

    public int estTime() {
        return (int) map.get("EST_TIME");
    }

    public int actualTime() {
        return (int) map.get("EST_TIME");
    }

    public String workRequired() {
        return (String) map.get("WORK_REQUIRED");
    }

    public String workCarried() {
        return (String) map.get("WORK_CARRIED");
    }

    public java.sql.Date date() {
        return (java.sql.Date) map.get("DATE");
    }

    public String itemIDs() {
        return "" + map.get("ITEM_IDS");
    }

    public List<JobStock> items() {
        return (List<JobStock>) map.get("ITEMS");
    }
    
    public String taskIDs(){
        return ""+map.get("TASK_IDS");
    }
    
    public ArrayList<Task> tasks(){
        return (ArrayList<Task>)map.get("TASKS");
    }

    public double cost() {
        return (double) map.get("COST");
    }

    public String mechanicIDs() {
        return (String) map.get("MECHANIC_IDS");
    }
    
    public JobType jobType(){
        return (JobType) map.get("JOB_TYPE");
    }
    
    public int jobTypeInt(){
        return (int) map.get("JOB_TYPE_ID");
    }

    public int statusInt() {
        return (int) map.get("STATUS");
    }
    
    public Boolean paid(){
        return (Boolean) map.get("PAID");
    }

    public JobStatusType status() {
        int statusInt = statusInt();

        switch (statusInt) {
            case 0:
                return JobStatusType.PENDING;
            case 1:
                return JobStatusType.IN_PROGRESS;
            case 2:
                return JobStatusType.COMPLETED;
            default:
                return JobStatusType.NONE;
        }
    }

    @Override
    public ItemType type() {
        return ItemType.JOB;
    }
    
    public ArrayList<Mechanic> mechanics(){
        return (ArrayList<Mechanic>)map.get("MECHANICS");
    }

    @Override
    public String uniqueStr() {
        return "WORK_CARRIED='NOCONTENT'";
    }

    @Override
    public Map<String, Object> map() {
        return map;
    }

    @Override
    public String IDStr() {
        return "JOB_ID=" + ID();
    }
}
