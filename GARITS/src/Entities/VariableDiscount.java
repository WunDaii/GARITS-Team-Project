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
public class VariableDiscount extends Discount {

    public VariableDiscount(String str) {

        List<String> tiersStr = Arrays.asList(str.split(","));
        Map<String, Double> tiersMap = new HashMap<>();

        for (String string : tiersStr) {
            List<String> tiersStrs_ = Arrays.asList(string.split("="));
            String key = tiersStrs_.get(0);
            Double val = Double.parseDouble(tiersStrs_.get(1));
            tiersMap.put(key, val);
        }

        setDiscountMap(tiersMap);
    }

    public Map<String, Double> discountMap() {
        return (HashMap<String, Double>) map.get("DISCOUNT_MAP");
    }

    public void setDiscountMap(Map<String, Double> discountMap) {
        map.put("DISCOUNT_MAP", discountMap);
    }

    @Override
    public Map<String, Object> map() {
        return map;
    }

    public Double calculateCost(Double cost, JobType jobType) {

        return cost - ((rate(cost, jobType) / 100) * cost);
    }

    @Override
    public Double calculateCost(Double cost) {
        return cost;
    }

    public Double rate(Double cost, JobType jobType) {
        Integer currentKey = 0;
        Map<String, Double> discountMap = discountMap();
        Double rate = 0.0;
        for (String key : discountMap.keySet()) {
            if (key.equalsIgnoreCase(jobType.name())) {
                rate = discountMap.get(key);
                break;
            }
        }
        return rate;
    }

    @Override
    public Double rate(Double cost) {
        return 0.0;
    }
}