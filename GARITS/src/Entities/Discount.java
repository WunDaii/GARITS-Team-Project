/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Map;

/**
 *
 * @author Daven
 */
public abstract class Discount extends Item {

        public enum DiscountType{
        NONE, FIXED_RATE, FLEXIBLE
    }
    
    @Override
    public ItemType type() {
        return ItemType.DISCOUNT;
    }
        
    public DiscountType discountType(){
        return DiscountType.NONE;
    }
        
    public int customerID(){
        return (int) map.get("CUSTOMER_ID");
    }
        
    @Override
    public String IDStr() {
        return "CUSTOMER_ID="+customerID();
    }

    @Override
    public String uniqueStr() {
        return IDStr();
    }
    
        @Override
    public Map<String, Object> map() {
        return map;
    }
    
    /**
     * Calculates the Job's cost using the Discount subclass's details.
     * For example, if the subclass is FixedDiscount, this method will subtract the
     * fixed discount rate from the cost passed in.
     * @param cost the initial cost of the Job before the discount is applied.
     * @return the final cost of the Job after the discount rate has been applied.
     * @see FixedDiscount
     * @see FlexibleDiscount
     * @see VariableDiscount
     */
    public abstract Double calculateCost(Double cost);
    
    /**
     * The discount rate that the discount provides.
     * Different Discount subclasses provide varying rates depending on
     * specific factors.  This method returns the final rate.
     * @param cost the initial cost of the Job before the discount is applied.
     * @return the discount rate in percentage that the Discount provides.
     * @see FixedDiscount
     * @see FlexibleDiscount
     * @see VariableDiscount
     */
    public abstract Double rate(Double cost);
}
