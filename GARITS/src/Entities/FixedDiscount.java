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
public class FixedDiscount extends Discount{
    
    public FixedDiscount(String string){
        
        setRate(Double.parseDouble(string));
    }
    
    public void setRate(Double rate){
        map.put("RATE", rate);
    }
    
    public Double rate(){
        return (Double)map.get("RATE");
    }
    
            @Override
    public Map<String, Object> map() {
        return map;
    }

    @Override
    public Double calculateCost(Double cost) {
        return cost - ((rate()/100) * cost);
    }

    @Override
    public Double rate(Double cost) {
        return rate();
    }
}
