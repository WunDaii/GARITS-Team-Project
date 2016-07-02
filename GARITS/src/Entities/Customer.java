/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import DatabasePackage.DBConnect;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Daven
 */
public class Customer extends Item {

    Map<String, Object> map = new HashMap<>();
//    String firstName = null, lastName = null, telephone = null, address = null, postCode = null, email = null, fax = null;
//    int ID;
//    java.util.Date dateCreated = new java.util.Date();
    
    public enum DiscountType {
        FIXED, FLEXIBLE, VARIABLE, NONE
    }

    public Customer(ResultSet rs) {
        try {
            map.put("CUSTOMER_ID", rs.getInt("CUSTOMER_ID"));
            map.put("FIRST_NAME", rs.getString("FIRST_NAME"));
            map.put("LAST_NAME", rs.getString("LAST_NAME"));
            map.put("TELEPHONE", rs.getString("TELEPHONE"));
            map.put("ADDRESS", rs.getString("ADDRESS"));
            map.put("POSTCODE", rs.getString("POSTCODE"));
            map.put("EMAIL", rs.getString("EMAIL"));
            map.put("FAX", rs.getString("FAX"));
            int disc = rs.getInt("DISCOUNT_TYPE");
            setDiscountInt(disc, rs.getString("DISCOUNT_STR"));
            map.put("DATE_CREATED", rs.getDate("DATE_CREATED"));
            map.put("VEHICLE_IDS", rs.getString("VEHICLE_IDS"));
            map.put("CUSTOMER_TYPE", rs.getInt("CUSTOMER_TYPE"));
            map.put("TITLE", rs.getString("TITLE"));
            
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Customer() {

    }

    public Customer(int ID, String firstName, String lastName) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setID(ID);
    }

    // Getters
    
    public String title(){
        return (String)map.get("TITLE");
    }
    
    public String firstName() {
        return (String) map.get("FIRST_NAME");
    }

    public String lastName() {
        return (String) map.get("LAST_NAME");
    }

    public String telephone() {
        return (String) map.get("TELEPHONE");
    }

    public int ID() {
        return (int) map.get("CUSTOMER_ID");
    }

    public String address() {
        return (String) map.get("ADDRESS");
    }

    public String postcode() {
        return (String) map.get("POSTCODE");
    }

    public String email() {
        return (String) map.get("EMAIL");
    }

    public String fax() {
        return (String) map.get("FAX");
    }
    
    public String vehicleIDs(){
        return (String)map.get("VEHICLE_IDS");
    }
    
    public int customerType(){
        return (int) map.get("CUSTOMER_TYPE");
    }
    
    public ArrayList<Vehicle> vehicles(){
        return (ArrayList<Vehicle>) map.get("VEHICLES");
    }

//    public String dateCreated() {
//        return (String) map.get("DATE_CREATED");
//    }

        public java.sql.Date dateCreated(){
    return (java.sql.Date) map.get("DATE_CREATED");
        }    

        public Discount discount(){
            return (Discount) map.get("DISCOUNT");
        }
        
    public DiscountType discountType(){
                int type = discountTypeInt();

        switch (type) {
            case 0:
                return DiscountType.NONE;
            case 1:
                return DiscountType.FIXED;
            case 2:
                return DiscountType.FLEXIBLE;
            case 3:
                return DiscountType.VARIABLE;
            default:
                return DiscountType.NONE;
        }
    }
    
    public String discountStr(){
        return (String)map.get("DISCOUNT_STR");
    }
    
    public int discountTypeInt(){
        return (int) map.get("DISCOUNT_TYPE");
    }

    // Setters
    public void setID(int ID) {
        map.put("CUSTOMER_ID", ID);
    }
    
    public void setTitle(String title){
        map.put("TITLE", title);
    }

    public void setFirstName(String firstName) {
        map.put("FIRST_NAME", firstName);
    }

    public void setLastName(String lastName) {
        map.put("LAST_NAME", lastName);
    }

    public void setTelephone(String telephone) {
        map.put("TELEPHONE", telephone);
    }

    public void setAddress(String address) {
        map.put("ADDRESS", address);
    }

    public void setPostcode(String postcode) {
        map.put("POSTCODE", postcode);
    }

    public void setEmail(String email) {
        map.put("EMAIL", email);
    }
    
    public void setCustomerType(int customerType){
        map.put("CUSTOMER_TYPE", customerType);
    }
    
    public void setDiscountStr(String string){
        map.put("DISCOUNT_STR", string);
    }

    public void setFax(String fax) {
        map.put("FAX", fax);
    }
    
    public void setDiscountInt(int discount, String string){
                map.put("DISCOUNT_TYPE", discount);
                setDiscountStr(string);
                Discount disc = new Discount() {
                    @Override
                    public Double calculateCost(Double cost) {
                        return cost;
                    }

                    @Override
                    public Double rate(Double cost) {
                        return 0.0;
                    }
                };
                
                switch (discountType()){
                    case FIXED:
                        disc = new FixedDiscount(string);
                        break;
                    case FLEXIBLE:
                        disc = new FlexibleDiscount(string);
                        break;
                    case VARIABLE:
                        disc = new VariableDiscount(string);
                        break;
                    default:
                             break;   
                }
                
                setDiscount(disc);
    }
    
    public void setDiscount(Discount discount){
        map.put("DISCOUNT", discount);
    }
    
    public void setDateCreated(){
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
    map.put("DATE_CREATED", date);
    }
    
    public void setVehicleIDs(String veh){
        map.put("VEHICLE_IDS", veh);
    }
    
    public void setVehicles(ArrayList<Vehicle> vehiclesArr){
        map.put("VEHICLES", vehiclesArr);
        
                ArrayList<String> strings = new ArrayList<>();

        for (Vehicle vehicle : vehiclesArr) {
            strings.add(vehicle.regNo());
        }

        String finalStr = String.join(",", strings);
        setVehicleIDs(finalStr);
    }

    @Override
    public ItemType type() {
        return ItemType.CUSTOMER;
       }

    @Override
    public String uniqueStr() {
      return "EMAIL='"+email()+"'";
    }
    
    @Override
    public Map<String, Object> map(){
        return map;
    }
    
    @Override
        public String IDStr() {
        return "CUSTOMER_ID=" + ID();
    }
}