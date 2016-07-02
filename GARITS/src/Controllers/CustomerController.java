/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DatabasePackage.DBConnect;
import Entities.Customer;
import Entities.Job;
import Entities.Task;
import Entities.Vehicle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daven
 */
public class CustomerController extends BaseController{
    
    /**
     * Gets a list of unpaid jobs for a Customer.
 *
 * @param  customer  the customer to check.
 * @return      list of Job objects that are not paid.
 * @see         Customer
 * @see Job
 */
        public ArrayList<Job> unpaidJobsForCustomer(Customer customer){
        
            ArrayList<Job> jobs = new ArrayList<>();
            String query = "SELECT JOBS.*, "+
                        "CUSTOMERS.*, "+
                        "VEHICLES.*, JOB_TYPES.* "+
                        "FROM Jobs LEFT OUTER JOIN CUSTOMERS "+
                        "ON JOBS.CUSTOMER_ID=CUSTOMERS.CUSTOMER_ID "+
                        "LEFT OUTER JOIN VEHICLES ON JOBS.VEHICLE_REG=VEHICLES.REG_NO "+
                        "LEFT OUTER JOIN JOB_TYPES ON JOBS.JOB_TYPE_ID=JOB_TYPES.JOB_TYPE_ID "+
                    "WHERE JOBS.CUSTOMER_ID="+customer.ID()+" AND PAID='false'";
            ResultSet rs = DBConnect.sharedInstance().executeSelectQuery(query);
            
            try {
                while (rs.next()){
                    jobs.add(new Job(rs));
                }
            } catch (SQLException ex) {
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return jobs;
    }
    
        /**
         * Adds the customer to the database.
         * This also sets the Customer's date created to the current date.
         * Returns true if the Customer has successfully been added to the database, or false if
         * a Customer with the same e-mail already exists.
         * @param customer the Customer to add to the database.
         * @return whether the customer has successfully been added to the database.
         * @see Customer
         */
    public boolean addCustomer(Customer customer){
        customer.setDateCreated();
        return addItem(customer);
    }
    
    /**
     * Updates the Customer's details in the database.
     * @param customer the Customer to be updated.
     * @see Customer
     */
    public void updateCustomer(Customer customer){
        super.updateItem(customer);
    }
    
    /**
     * Gets a list of vehicles that the Customer owns.
     * @param customer the Customer to get the vehicles for.
     * @return a list of Vehicles that the Customer owns.
     * @see Customer
     * @see Vehicle
     */
        public ArrayList<Vehicle> getVehiclesForCustomer(Customer customer) {

        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

        String query = "SELECT VEHICLES.*, CUSTOMERS.FIRST_NAME,CUSTOMERS.LAST_NAME, CUSTOMERS.CUSTOMER_ID "+
                "FROM VEHICLES "+
                "LEFT OUTER JOIN CUSTOMERS "+
                "ON VEHICLES.CUSTOMER_ID=CUSTOMERS.CUSTOMER_ID " + 
                        "WHERE VEHICLES.CUSTOMER_ID=" + customer.ID();

        ResultSet rs = DBConnect.sharedInstance().executeSelectQuery(query);

        try {

            while (rs.next()) {
                Vehicle vehicle = new Vehicle(rs);
                vehicles.add(vehicle);
            }

        } catch (SQLException ex) {
            Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("getVehiclesForCustomer - end");
        return vehicles;
    }
}