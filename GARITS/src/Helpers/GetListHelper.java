/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import Entities.Customer;
import DatabasePackage.DBConnect;
import Entities.Item.ItemType;
import Entities.*;
import Entities.Vehicle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daven
 */
public class GetListHelper {

    public GetListHelper() {

    }

    /**
     * Gets a list of Items of the requested ItemType.
     *
     * @param type the requested ItemType.
     * @return a list of Items of the requested ItemType.
     * @see Item
     * @see ItemType
     */
    public ArrayList<? extends Item> getListForListType(ItemType type) {

        String tableName = tableNameForItemType(type);

        ArrayList<Item> items = new ArrayList<>();
        String query = "SELECT * FROM ";
        switch (type) {
            case JOB:
                query = "SELECT JOBS.*, "
                        + "CUSTOMERS.*, "
                        + "VEHICLES.*, JOB_TYPES.* "
                        + "FROM Jobs LEFT OUTER JOIN CUSTOMERS "
                        + "ON JOBS.CUSTOMER_ID=CUSTOMERS.CUSTOMER_ID "
                        + "LEFT OUTER JOIN VEHICLES ON JOBS.VEHICLE_REG=VEHICLES.REG_NO "
                        + "LEFT OUTER JOIN JOB_TYPES ON JOBS.JOB_TYPE_ID=JOB_TYPES.JOB_TYPE_ID";
                break;
            case MECHANIC:
                query = query + "Users WHERE USER_TYPE=3";
                break;
            case VEHICLE:
                query = "SELECT VEHICLES.*, CUSTOMERS.CUSTOMER_ID, CUSTOMERS.FIRST_NAME, CUSTOMERS.LAST_NAME FROM VEHICLES LEFT OUTER JOIN CUSTOMERS ON VEHICLES.CUSTOMER_ID=CUSTOMERS.CUSTOMER_ID";
                break;
            case STOCK:
                query = "SELECT STOCK.*, SPARE_PARTS.*, SUPPLIERS.* "
                        + "FROM STOCK "
                        + "LEFT OUTER JOIN SPARE_PARTS "
                        + "ON STOCK.ITEM_NUMBER=SPARE_PARTS.ITEM_NUMBER "
                        + "LEFT OUTER JOIN SUPPLIERS "
                        + "ON SPARE_PARTS.SUPPLIER_ID=SUPPLIERS.SUPPLIER_ID";
                break;
            case PART_ORDER:
                query = "SELECT PART_ORDER.*, CUSTOMERS.* FROM PART_ORDER "
                        + "LEFT OUTER JOIN CUSTOMERS "
                        + "ON PART_ORDER.CUST_ID=CUSTOMERS.CUSTOMER_ID";
                break;
            case PART:
                query = "SELECT SPARE_PARTS.*, SUPPLIERS.* "
                        + "FROM SPARE_PARTS "
                        + "LEFT OUTER JOIN SUPPLIERS "
                        + "ON SPARE_PARTS.SUPPLIER_ID=SUPPLIERS.SUPPLIER_ID";
                break;
            default:
                query = query + tableName;
                break;
        }

        ResultSet rs = DBConnect.sharedInstance().executeSelectQuery(query);

        try {

            while (rs.next()) {
                Item item = null;
                switch (type) {
                    case CUSTOMER:
                        item = new Customer(rs);
                        break;
                    case JOB:
                        item = new Job(rs);
                        break;
                    case USER:
                        if (rs.getInt("USER_TYPE") == 3) {
                            item = new Mechanic(rs);
                        } else {
                            item = new User(rs);
                        }
                        break;
                    case MECHANIC:
                        item = new Mechanic(rs);
                        break;
                    case PART:
                        item = new Part(rs);
                        break;
                    case TASK:
                        item = new Task(rs);
                        break;
                    case VEHICLE:
                        item = new Vehicle(rs);
                        break;
                    case STOCK:
                        item = new Stock(rs);
                        break;
                    case PART_ORDER:
                        item = new PartOrder(rs);
                        break;
                    case SUPPLIER:
                        item = new Supplier(rs);
                        break;
                    case JOB_TYPE:
                        item = new JobType(rs);
                        break;
                    default:
                }

                items.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return items;
    }

    /**
     * Gets the relevant table name for the requested ItemType.
     * This is used when querying the database.
     * @param type the requested ItemType.
     * @return the table name for the requested ItemType.
     * @see Item
     * @see ItemType
     */
    public String tableNameForItemType(ItemType type) {
        switch (type) {
            case USER:
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
            case SUPPLIER:
                return "SUPPLIERS";
            case JOB_TYPE:
                return "JOB_TYPES";
            default:
                return "";
        }
    }
}
