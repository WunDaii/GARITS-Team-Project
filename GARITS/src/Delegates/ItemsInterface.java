/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Delegates;

import Entities.Item;
import java.util.ArrayList;

/**
 *
 * @author Daven
 */
public interface ItemsInterface {
    
    /**
     * Sends a list of Items to the class.
     * @param items a list of items that have been selected.
     * @see Item
     */
    public void selectedItems(ArrayList<? extends Item> items);
    
    /**
     * Sends a list of Items that have been updated.
     * @param items a list of items that have been updated.
     * @see Item
     */
    public void updatedItems(ArrayList<? extends Item> items);
}