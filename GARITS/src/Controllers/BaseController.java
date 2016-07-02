/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Item;
import DatabasePackage.DBConnect;
import Entities.Customer;
import Entities.Item.ItemType;
import Entities.Mechanic;
import Entities.Part;
import Entities.Stock;
import Entities.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daven
 */
public class BaseController {

    /**
 * Adds an item object to its relevant table in the database. 
 * The item must be a valid subclass of Item.  Returns true if
 * the item has been added, or false if the item has not been
 * added because it already exists.
 *
 * @param  item  the item to be added to the database.
 * @return      whether the item has successfully been added to the database.
 * @see         Item
 */
    public boolean addItem(Item item) {

        try {
            if (alreadyExists(item)) {
                return false;
            }

            String insertStr = item.insertString();
            String valuesStr = item.valuesString();

            String query = "INSERT INTO " + tableNameForItem(item) + "(" + insertStr + ") VALUES(" + valuesStr + ")";
                        DBConnect.sharedInstance().executeUpdateQuery(query);

            if (item.type() == Item.ItemType.STOCK){
                            System.out.println("Item == STOCK");

                Part part = ((Stock)item).part();
                addItem(part);
            }
            
            System.out.println("Adding item > true");
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
 * Updates an item object in its relevant table in the database. 
 * The item must be a valid subclass of Item.
 *
 * @param  item  the item to be updated in the database.
 * @see         Item
 */
    public void updateItem(Item item) {
        String update = "UPDATE " + tableNameForItem(item);
        String set = "SET " + item.setString();
        String where = "WHERE " + item.IDStr();
        String query = update + " " + set + " " + where;

        DBConnect.sharedInstance().executeUpdateQuery(query);
    }
    
    /**
 * Deletes an item object from its relevant table in the database. 
 * The item must be a valid subclass of Item.
 *
 * @param  item  the item to be deleted from the database.
 * @see         Item
 */
    public void deleteItem(Item item) {
       
        String query = "DELETE FROM " + tableNameForItem(item) + " WHERE " + item.IDStr();
        DBConnect.sharedInstance().executeDeleteQuery(query);
        
        if (item.type() == ItemType.PART){
                    query = "DELETE FROM STOCK WHERE " + item.IDStr();
        DBConnect.sharedInstance().executeDeleteQuery(query);

        }else if (item.type() == ItemType.STOCK){
            deleteItem(((Stock)item).part());
        }
    }

    /**
 * Checks whether an item object already exists in its relevant table in the database. 
 * The item must be a valid subclass of Item.  Returns true if
 * the item already exists in the database, or false if it does not.
 * <p>
 * This method uses the Item's uniqueString method to check whether an existing
 * row exists with the same data.
 *
 * @param  item  the item to check if it already exists in the database.
 * @return      whether the item already exists in the database.
 * @throws java.sql.SQLException an error when connecting to the database.
 * @see         Item
 */
    public boolean alreadyExists(Item item) throws SQLException {
        String SQL = "SELECT *";
        String from = "FROM " + tableNameForItem(item);
        String where = "WHERE " + item.uniqueStr();
        String query = SQL + " " + from + " " + where;

        ResultSet rs = DBConnect.sharedInstance().executeSelectQuery(query);

        int rowNum = 0;
        while (rs.next()) {
            return true;
        }

        return false;
    }
    
    /**
 * Returns the name of the table in which the Item relates to. 
 * The item must be a valid subclass of Item with a valid 'ItemType'.
 *
 * @param  item  the item to be checked.
 * @return      the name of the table.
 * @see         Item
 */
    public String tableNameForItem(Item item) {
        switch (item.type()) {
            case USER:
                return "USERS";
            case MECHANIC:
                return "USERS";
            case CUSTOMER:
                return "CUSTOMERS";
            case JOB:
                return "JOBS";
            case VEHICLE:
                return "VEHICLES";
            case PART:
                return "SPARE_PARTS";
            case TASK:
                return "TASKS";
            case STOCK:
                return "STOCK";
            case PART_ORDER:
                return "PART_ORDER";
            case SUPPLIER:
                return "SUPPLIERS";
            case JOB_TYPE:
                return "JOB_TYPES";
            default:
                return "";
        }
    }
}