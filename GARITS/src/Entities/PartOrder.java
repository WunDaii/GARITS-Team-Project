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
public class PartOrder extends Item {

    public PartOrder(ResultSet rs) {
        try {
            setID(rs.getInt("PART_ORDER_ID"));
            setPartIDs(rs.getString("PARTS"));
            setCost(rs.getDouble("COST"));
            setDate(rs.getDate("DATE"));
            setCustomer(new Customer(rs));
            setPaid(rs.getBoolean("PAID"));
        } catch (SQLException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PartOrder() {
    }

    public void setID(int ID) {
        map.put("PART_ORDER_ID", ID);
    }

    public void setCost(Double cost) {
        map.put("COST", cost);
    }

    public void setDate(java.sql.Date date) {
        map.put("DATE", date);
    }

    public void setCustomerID(int custID) {
        map.put("CUST_ID", custID);
    }

    public void setCustomer(Customer cust) {
        setCustomerID(cust.ID());
        map.put("CUSTOMER", cust);
    }

    public void setPartIDs(String itemIDs) {
        map.put("PARTS", itemIDs);
    }

    public void setPaid(Boolean paid) {
        map.put("PAID", paid);
    }

    public void setParts(List<JobStock> items) {
        map.put("ITEMS", items);

        ArrayList<String> strings = new ArrayList<>();

        for (JobStock stock : items) {
            strings.add(stock.itemNumber() + "=" + stock.jobQuantity());
        }

        String finalStr = String.join(",", strings);
        setPartIDs(finalStr);
    }

    // GETTERS:
    public int ID() {
        return (int) map.get("PART_ORDER_ID");
    }

    public String partIDs() {
        return "" + map.get("PARTS");
    }

    public List<JobStock> parts() {
        return (List<JobStock>) map.get("ITEMS");
    }

    public Double cost() {
        return (Double) map.get("COST");
    }

    public java.sql.Date date() {
        return (java.sql.Date) map.get("DATE");
    }

    public int customerID() {
        return (int) map.get("CUST_ID");
    }

    public Customer customer() {
        return (Customer) map.get("CUSTOMER");
    }

    public Boolean paid() {
        return (Boolean) map.get("PAID");
    }

    @Override
    public Map<String, Object> map() {
        return map;
    }

    @Override
    public ItemType type() {
        return ItemType.PART_ORDER;
    }

    @Override
    public String uniqueStr() {
        return "PARTS='NOCONTENT'";
    }

    @Override
    public String IDStr() {
        return "PART_ORDER_ID=" + ID();
    }
}
