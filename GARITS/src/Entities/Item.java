/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 *
 * @author Daven
 */
public abstract class Item {

    Map<String, Object> map = new HashMap<>();

    public enum ItemType {
        JOB, USER, CUSTOMER, PART, VEHICLE, TASK, MECHANIC, INVOICE, STOCK, PART_ORDER, SUPPLIER, JOB_TYPE, DISCOUNT, NONE
    }

    /**
     * Gets the type of Item subclass. This is used to distinguish between the
     * different Items, especially useful when deciding which table to use when
     * connecting to the database.
     *
     * @return type of Item subclass.
     */
    public abstract ItemType type();

    /**
     * Gets a string of column names used for the 'INSERT' SQL statement.
     *
     * @return a comma-separated String of column names.
     */
    public String insertString() {
        Set<String> keys = map().keySet();
        keys.removeIf(new ItemPredicate<>(map()));
        return String.join(",", keys);
    }

    /**
     * Gets a string of values for inserting a new row into the database.
     *
     * @return a comma-separated String of values for columns.
     */
    public String valuesString() {
        ArrayList<String> values = new ArrayList<String>();

        for (String key : map().keySet()) {
            String value;
            Object object = map().get(key);

            if (object instanceof Integer || object instanceof Double) {
                value = "" + map().get(key);
                values.add(value);
            } else if (object instanceof java.util.Date) {
                java.util.Date dateCreated = (java.util.Date) object;
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                value = df.format(dateCreated);
                values.add("'" + value + "'");
            } else if (object instanceof Boolean) {
                Boolean bool = (Boolean) object;
                if (bool == true) {
                    value = "true";
                } else {
                    value = "false";
                }
                values.add("'" + value + "'");
            } else if (object instanceof String) {
                value = (String) map().get(key);
                values.add("'" + value + "'");
            }
        }

        return String.join(",", values);
    }

    /**
     * Gets a string for an 'UPDATE' SQL statement.
     *
     * @return a comma-separated string for updating values in the database.
     */
    public String setString() {
        ArrayList<String> values = new ArrayList<String>();

        for (String key : map().keySet()) {
            String value;
            Object object = map().get(key);
            if ((IDStr().contains(key) && key.contains("ID")) || object instanceof List) {
                continue;
            } else if (key.equals("DATE_CREATED")) {
                continue;
            } else if (object instanceof Integer || object instanceof Double) {
                value = "" + map().get(key);
            } else if (object instanceof java.util.Date) {
                java.util.Date dateCreated = (java.util.Date) object;
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                value = "'" + df.format(dateCreated) + "'";
            } else if (object instanceof String) {
                value = "'" + (String) map().get(key) + "'";
            } else if (object instanceof Boolean) {
                Boolean bool = (Boolean) object;
                if (bool == true) {
                    value = "'true'";
                } else {
                    value = "'false'";
                }
            } else {
                continue;
            }

            values.add(key + "=" + value);
        }

        return String.join(",", values);
    }

    /**
     * A String used to identify the Item in its relevant table in the database.
     * For example, for a Customer object, this wold include its CUSTOMER_ID.
     *
     * @return the string used to identify the Item.
     */
    public abstract String IDStr();

    /**
     * A String used to check if an Item already exists in the database. For
     * example, for a Customer object, this would include its e-mail. This is
     * because only one Customer can exist per e-mail.
     *
     * @return a String used to check if an Item already exists in the database.
     */
    public abstract String uniqueStr();

    /**
     * A Map where all the values are stored. The keys for each value are the
     * same as the column names in the appropriate table for the Item.
     *
     * @return the Map where all values are stored.
     */
    public Map<String, Object> map() {
        return map;
    }
}

class ItemPredicate<t> implements Predicate<t> {

    Map<String, Object> map;

    public ItemPredicate(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * Removes any objects that are instances of Item or List. This is because
     * we cannot get an appropriate value from these classes to insert into the
     * database.
     *
     * @param t the object to check.
     * @return whether the object should be removed from the ArrayList.
     */
    @Override
    public boolean test(t t) {
        if (t instanceof String) {
            return map.get(t) instanceof Item || map.get(t) instanceof List;
        }
        return true;
    }
}
