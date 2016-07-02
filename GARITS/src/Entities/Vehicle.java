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
public class Vehicle extends Item {

    public Vehicle(ResultSet rs) {
        try {

            setRegNo(rs.getString("REG_NO"));
            setMake(rs.getString("MAKE"));
            setModel(rs.getString("MODEL"));
            setEngSerial(rs.getString("ENG_SERIAL"));
            setChassisNumber(rs.getString("CHASSIS_NUMBER"));
            setColour(rs.getString("COLOUR"));
            setMOTDate(rs.getDate("MOT_DATE"));
            setYear(rs.getInt("VEHICLE_YEAR"));
            Customer customer = new Customer();
            customer.setID(rs.getInt("CUSTOMER_ID"));
            customer.setFirstName(rs.getString("FIRST_NAME"));
            customer.setLastName(rs.getString("LAST_NAME"));
            setCustomer(customer);

        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Vehicle() {

    }

    public String regNo() {
        return (String) map.get("REG_NO");
    }

    public String make() {
        return (String) map.get("MAKE");
    }

    public String model() {
        return (String) map.get("MODEL");
    }

    public int year() {
        return (int) map.get("VEHICLE_YEAR");
    }

    public String engSerial() {
        return (String) map.get("ENG_SERIAL");
    }

    public String chassisNumber() {
        return (String) map.get("CHASSIS_NUMBER");
    }

    public String colour() {
        return (String) map.get("COLOUR");
    }

    public int customerID() {
        return (int) map.get("CUSTOMER_ID");
    }

    public Customer customer() {
        return (Customer) map.get("CUSTOMER");
    }

    public java.sql.Date MOTDate() {
        return (java.sql.Date) map.get("MOT_DATE");
    }

    // Setters
    public void setRegNo(String string) {
        map.put("REG_NO", string);
    }

    public void setMake(String string) {
        map.put("MAKE", string);
    }

    public void setModel(String string) {
        map.put("MODEL", string);
    }

    public void setEngSerial(String string) {
        map.put("ENG_SERIAL", string);
    }

    public void setChassisNumber(String string) {
        map.put("CHASSIS_NUMBER", string);
    }

    public void setColour(String string) {
        map.put("COLOUR", string);
    }

    public void setCustomerID(int customerID) {
        map.put("CUSTOMER_ID", customerID);
    }

    public void setCustomer(Customer customer) {
        map.put("CUSTOMER", customer);
        setCustomerID(customer.ID());
    }

    public void setMOTDate(java.sql.Date date) {
        map.put("MOT_DATE", date);
    }

    public void setYear(int year) {
        map.put("VEHICLE_YEAR", year);
    }

    @Override
    public ItemType type() {
        return ItemType.VEHICLE;
    }

    @Override
    public String uniqueStr() {
        return "REG_NO=" + "'" + regNo() + "'";
    }

    @Override
    public String IDStr() {
        return uniqueStr();
    }

    @Override
    public Map<String, Object> map() {
        return map;
    }
}
