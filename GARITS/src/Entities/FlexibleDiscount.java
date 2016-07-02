/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Daven
 */
public class FlexibleDiscount extends Discount {

    public FlexibleDiscount(String str) {

        // 1000=10,2000=20,3000=30
        List<String> tiersStr = Arrays.asList(str.split(","));
        Map<Integer, Double> tiersMap = new HashMap<>();

        for (String string : tiersStr) {
            List<String> tiersStrs_ = Arrays.asList(string.split("="));
            Integer key = Integer.parseInt(tiersStrs_.get(0));
            Double val = Double.parseDouble(tiersStrs_.get(1));
            tiersMap.put(key, val);
        }

        setDiscountMap(tiersMap);
    }

    public Map<Integer, Double> discountMap() {
        return (HashMap<Integer, Double>) map.get("DISCOUNT_MAP");
    }

    public void setDiscountMap(Map<Integer, Double> discountMap) {
        map.put("DISCOUNT_MAP", discountMap);
    }

    @Override
    public Map<String, Object> map() {
        return map;
    }

    @Override
    public Double calculateCost(Double cost) {
        return cost - (rate(cost)/100 * cost);
    }

    @Override
    public Double rate(Double cost) {
        Integer currentKey = 0;
        Map<Integer, Double> discountMap = discountMap();
        for (Integer key : discountMap.keySet()) {
            if (cost >= discountMap.get(key) && currentKey < key) {
                currentKey = key;
            }
        }
        return discountMap.get(currentKey);
    }
}
