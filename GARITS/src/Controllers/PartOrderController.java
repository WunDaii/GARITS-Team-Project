/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DatabasePackage.DBConnect;
import Entities.PartOrder;
import Entities.JobStock;
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
public class PartOrderController extends BaseController {
    
    /**
     * Gets a list of JobStock items for the PartOrder.
     * @param order the order to retrieve the JobStock items for.
     * @return a list of JobStock items for the PartOrder.
     * @see PartOrder
     * @see JobStock
     */
    
        public ArrayList<JobStock> getStockForPartOrder(PartOrder order) {

        ArrayList<JobStock> jobParts = new ArrayList<>();

        if (order.partIDs().length() == 0 || order.partIDs().equals("null")) {
            System.out.println("getStockForPartOrder > partIDs length = 0, returning");
            return jobParts;
        }

        List<String> jobItemStrs = Arrays.asList(order.partIDs().split(","));
        Map<String, Integer> partsMap = new HashMap<>();

        for (String string : jobItemStrs) {
            List<String> jobItemStr_ = Arrays.asList(string.split("="));
            String jobItemID = jobItemStr_.get(0);
            Integer quantity = Integer.parseInt(jobItemStr_.get(1));
            partsMap.put(jobItemID, quantity);
        }

        ArrayList<String> whereStrs = new ArrayList<>();

        partsMap.keySet().stream().forEach((key) -> {
            whereStrs.add("STOCK.ITEM_NUMBER='" + key + "'");
            });

        String whereIDs = String.join(" OR ", whereStrs);

        String query = "SELECT STOCK.*, SPARE_PARTS.*, SUPPLIERS.* "+
                "FROM STOCK LEFT OUTER JOIN SPARE_PARTS "+
                "ON STOCK.ITEM_NUMBER=SPARE_PARTS.ITEM_NUMBER "+
                "LEFT OUTER JOIN SUPPLIERS "+
                "ON SPARE_PARTS.SUPPLIER_ID=SUPPLIERS.SUPPLIER_ID "+
                "WHERE " + whereIDs;

        System.out.println("QUERY: " + query);

        ResultSet rs = DBConnect.sharedInstance().executeSelectQuery(query);

        try {

            while (rs.next()) {
                String partID = rs.getString("ITEM_NUMBER");
                JobStock jobPart = new JobStock(rs, partsMap.get(partID));
                jobParts.add(jobPart);
            }

        } catch (SQLException ex) {
            Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jobParts;
    }
    
        /**
         * Updates the PartOrder's details in the database.
         * If the PartOrder has any stock, their quantities will also be updated.
         * @param order the order to update.
         * @see PartOrder
         * @see JobStock
         */
        public void updateOrder(PartOrder order) {
        super.updateItem(order);
        List<JobStock> stocks = order.parts();
        
        for (JobStock stock : stocks){
            super.updateItem(stock);
        }
    }
}
