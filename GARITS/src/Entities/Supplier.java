package Entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Daven
 */
public class Supplier extends Item {

    public Supplier(ResultSet rs) {
        try {
            setID(rs.getInt("SUPPLIER_ID"));
            setAddress(rs.getString("SUPPLIER_ADDRESS"));
            setPartIDs(rs.getString("SPARE_PARTS"));
            setPostCode(rs.getString("SUPPLIER_POSTCODE"));
            setName(rs.getString("SUPPLIER_NAME"));
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Supplier() {

    }

    // SETTERS:
    public void setID(int ID) {
        map().put("SUPPLIER_ID", ID);
    }

    public void setPartIDs(String parts) {
        map().put("SPARE_PARTS", parts);
    }

    public void setParts(ArrayList<Part> parts) {
        ArrayList<String> strings = new ArrayList<>();

        for (Part part : parts) {
            strings.add(part.number());
        }

        setPartIDs(String.join(",", strings));
        map.put("PARTS", parts);
    }

    public void setAddress(String add) {
        map().put("SUPPLIER_ADDRESS", add);
    }

    public void setPostCode(String postCode) {
        map().put("SUPPLIER_POSTCODE", postCode);
    }

    public void setName(String name) {
        map().put("SUPPLIER_NAME", name);
    }

    // GETTERS:
    public int ID() {
        return (int) map().get("SUPPLIER_ID");
    }

    public String name() {
        return (String) map().get("SUPPLIER_NAME");
    }

    public String address() {
        return (String) map().get("SUPPLIER_ADDRESS");
    }

    public String postcode() {
        return (String) map().get("SUPPLIER_POSTCODE");
    }

    public String partIDs() {
        return (String) map().get("SPARE_PARTS");
    }

    public ArrayList<Part> parts() {
        return (ArrayList<Part>) map.get("PARTS");
    }

    @Override
    public Map<String, Object> map() {
        return this.map;
    }

    @Override
    public ItemType type() {
        return ItemType.SUPPLIER;
    }

    @Override
    public String IDStr() {
        return "SUPPLIER_ID=" + ID();
    }

    @Override
    public String uniqueStr() {
        return "SUPPLIER_NAME='" + name() + "'";
    }
}
