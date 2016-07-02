/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import DatabasePackage.DBConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daven
 */
public class ConfigHelper {

    /**
     * Gets the VAT rate stored in the database.
     *
     * @return the VAT rate as a percentage.
     */
    public Double getVATRate() {

        Double rate = 0.0;

        String query = "SELECT VALUE FROM CONFIG WHERE CONFIG_KEY='VAT'";
        ResultSet rs = DBConnect.sharedInstance().executeSelectQuery(query);

        try {
            while (rs.next()) {
                rate = rs.getDouble("VALUE");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConfigHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rate;
    }

    /**
     * Gets the markup rate for parts stored in the database.
     *
     * @return the markup rate for parts as a percentage.
     */
    public Double getMarkupRate() {

        Double rate = 0.0;

        String query = "SELECT VALUE FROM CONFIG WHERE CONFIG_KEY='MARKUP'";
        ResultSet rs = DBConnect.sharedInstance().executeSelectQuery(query);

        try {
            while (rs.next()) {
                rate = rs.getDouble("VALUE");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConfigHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rate;
    }

    /**
     * Updates the VAT rate in the database.
     *
     * @param rate the new VAT rate as a percentage.
     */
    public void updateVAT(Double rate) {
        String query = "UPDATE CONFIG SET VALUE=" + rate + " WHERE CONFIG_KEY='VAT'";
        DBConnect.sharedInstance().executeUpdateQuery(query);
    }

    /**
     * Updates the markup rate for parts in the database.
     *
     * @param rate the new markup rate for parts as a percentage.
     */
    public void updateMarkup(Double rate) {
        String query = "UPDATE CONFIG SET VALUE=" + rate + " WHERE CONFIG_KEY='MARKUP'";
        DBConnect.sharedInstance().executeUpdateQuery(query);
    }
}
