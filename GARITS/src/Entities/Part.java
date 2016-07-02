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
public class Part extends Item {

    public Part(ResultSet rs) {
        try {
            map.put("ITEM_NUMBER", rs.getString("ITEM_NUMBER"));
            map.put("NAME", rs.getString("NAME"));
            map.put("DESCRIPTION", rs.getString("DESCRIPTION"));
            map.put("MANUFACTURER", rs.getString("MANUFACTURER"));
            map.put("VEHICLE_TYPE", rs.getString("VEHICLE_TYPE"));
            map.put("YEARS", rs.getString("YEARS"));
            setThreshold(rs.getInt("THRESHOLD"));
            setSupplier(new Supplier(rs));
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Part() {

    }

    public String name() {
        return (String) map.get("NAME");
    }

    public String description() {
        return (String) map.get("DESCRIPTION");
    }

    public String manufacturer() {
        return (String) map.get("MANUFACTURER");

    }

    public String vehicleType() {
        return (String) map.get("VEHICLE_TYPE");

    }

    public String years() {
        return (String) map.get("YEARS");

    }

    public String number() {
        return (String) map.get("ITEM_NUMBER");
    }

    public Supplier supplier() {
        return (Supplier) map.get("SUPPLIER");
    }

    public int threshold() {
        return (int) map.get("THRESHOLD");
    }

    public int supplierID() {
        return (int) map.get("SUPPLIER_ID");
    }

    // SETTERS:
    public void setNumber(String number) {
        map.put("ITEM_NUMBER", number);
    }

    public void setName(String name) {
        map.put("NAME", name);
    }

    public void setThreshold(int thres) {
        map.put("THRESHOLD", thres);
    }

    public void setManufacturer(String manu) {
        map.put("MANUFACTURER", manu);
    }

    public void setVehicleType(String type) {
        map.put("VEHICLE_TYPE", type);
    }

    public void setYears(String years) {
        map.put("YEARS", years);
    }

    public void setDescription(String descrip) {
        map.put("DESCRIPTION", descrip);
    }

    public void setSupplier(Supplier supplier) {
        map.put("SUPPLIER_ID", supplier.ID());
        map.put("SUPPLIER", supplier);
    }

    @Override
    public ItemType type() {
        return ItemType.PART;
    }

    @Override
    public String uniqueStr() {
        return "ITEM_NUMBER='" + number() + "'";
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
