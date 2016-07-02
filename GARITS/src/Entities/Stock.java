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
public class Stock extends Item {

    Part part;

    public Stock(ResultSet rs) {
        try {
            setItemNumber(rs.getString("ITEM_NUMBER"));
            setQuantity(rs.getInt("QUANTITY"));
            setCost(rs.getDouble("COST"));
            setInitialQuantity(rs.getInt("INITIAL_QUANTITY"));
            setUsedQuantity(rs.getInt("USED_QUANTITY"));
            Part part = new Part(rs);
            setPart(part);
            System.out.println("Part: " + part().number());
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Stock() {

    }

    public void setPart(Part part) {
        this.part = part;
    }

    public void setItemNumber(String number) {
        map.put("ITEM_NUMBER", number);
    }

    public void setQuantity(int quan) {
        map.put("QUANTITY", quan);
    }

    public void setCost(Double cost) {
        map.put("COST", cost);
    }

    public void setInitialQuantity(int quan) {
        map.put("INITIAL_QUANTITY", quan);
    }

    public void setUsedQuantity(int quan) {
        map.put("USED_QUANTITY", quan);
    }

    // GETTERS:
    public Part part() {
        return part;
    }

    public String itemNumber() {
        return (String) map.get("ITEM_NUMBER");
    }

    public int quantity() {
        return (int) map.get("QUANTITY");
    }

    public Double cost() {
        return (Double) map.get("COST");
    }

    @Override
    public ItemType type() {
        return ItemType.STOCK;
    }

    @Override
    public String IDStr() {
        return part().IDStr();
    }

    @Override
    public String uniqueStr() {
        return part().uniqueStr();
    }

    @Override
    public Map<String, Object> map() {
        return map;
    }
}
