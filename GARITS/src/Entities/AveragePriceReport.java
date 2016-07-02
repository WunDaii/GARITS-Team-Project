/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Controllers.JobController;
import Helpers.GetListHelper;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Daven
 */
public class AveragePriceReport {
    
    Double jobAverage = 0.0;
    HashMap<String, Double> mechanicsAverage = new HashMap<>();

    public AveragePriceReport() {
        
                GetListHelper helper = new GetListHelper();
        List<Job> jobs = (List<Job>) helper.getListForListType(Item.ItemType.JOB);
        List<Mechanic> mechanics = (List<Mechanic>) helper.getListForListType(Item.ItemType.MECHANIC);
        
        Double jobTotal = 0.0;
                
                HashMap<String, HashMap<String, Double>> mechTotal = new HashMap<>();
        mechanics.stream().forEach((mech) -> {
            HashMap<String, Double> map = new HashMap<>();
            map.put("Count", 0.0);
            map.put("Total", 0.0);
            mechTotal.put(keyFromMech(mech), map);
        });
        
                JobController jobController = new JobController();

                for (Job job : jobs){
            jobTotal += job.cost();

                            job.setMechanics(jobController.getMechanicsForJob(job));

            job.mechanics().stream().forEach((mech) -> {
                HashMap<String, Double> map = mechTotal.get(keyFromMech(mech));

                Double initial = map.get("Total");
                Double count = map.get("Count");
                map.put("Count", count + 1);
                map.put("Total", initial + job.cost());
                mechTotal.put(keyFromMech(mech), map);
            });
                }
                
                        mechTotal.keySet().stream().forEach((String key) -> {
            HashMap<String, Double> map = mechTotal.get(key);
            Double total = map.get("Total");
            Double count = map.get("Count");
            Double result = 0.0;
                        
            if (total > count && total > 0 && count > 0){
                result = total/count;
            }
                        mechanicsAverage.put(key, result);
        });
        
        jobAverage = jobTotal/jobs.size();
        
    }
    
    /**
     * Creates a key used for the map when initialising this class.
     * @param mech the mechanic for which the key will be created for.
     * @return the key.
     */
    public static String keyFromMech(Mechanic mech){
        return "" + mech.ID() +") "+ mech.firstName() + " " + mech.lastName();
    }
    
    /**
     * The average price across all jobs.
     * @return average price across all jobs.
     */
    public Double jobAverage(){
        return jobAverage;
    }
    
    /**
     * The map where the average price for each mechanic is stored.
     * @return map where average price for each mechanic is stored.
     */
    public HashMap<String, Double> mechanicAverage(){
        return mechanicsAverage;
    }
}