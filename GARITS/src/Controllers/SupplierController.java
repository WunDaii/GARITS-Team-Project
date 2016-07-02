/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DatabasePackage.DBConnect;
import Entities.Part;
import Entities.Supplier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daven
 */
public class SupplierController extends BaseController {

    /**
     * Gets a list of Parts that the Supplier provides.
     *
     * @param supplier the Supplier for which the Parts will be retrieved for.
     * @return a list of Part objects for the Supplier.
     * @see Supplier
     * @see Part
     */
    public ArrayList<Part> getPartsForSupplier(Supplier supplier) {
        ArrayList<Part> parts = new ArrayList<>();

        String query = "SELECT SPARE_PARTS.*, SUPPLIERS.* FROM SPARE_PARTS "
                + "LEFT OUTER JOIN SUPPLIERS "
                + "ON SPARE_PARTS.SUPPLIER_ID=SUPPLIERS.SUPPLIER_ID "
                + "WHERE SUPPLIERS.SUPPLIER_ID=" + supplier.ID();

        ResultSet rs = DBConnect.sharedInstance().executeSelectQuery(query);

        try {

            while (rs.next()) {
                Part part = new Part(rs);
                parts.add(part);
            }

        } catch (SQLException ex) {
            Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return parts;
    }
}