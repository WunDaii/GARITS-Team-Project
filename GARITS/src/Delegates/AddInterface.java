/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Delegates;

import Entities.Item;
import Panels.BasePanel;

/**
 *
 * @author Daven
 */
public interface AddInterface {
    
    /**
     * Sets the delegate to refresh when an item is added.
     * @param panel the BasePanel object to access.
     * @see BasePanel
     */
    public void setDelegate(BasePanel panel);
    
    /**
     * Sets the fields on the JFrame to correspond with the Item.
     * @param item the item to edit.
     * @see Item
     */
    public void editItem(Item item);
    
    /**
     * Sets the JFrame's visibility.
     * @param visible if the JFrame should be visible.
     */
    public void setVisible(boolean visible);
}