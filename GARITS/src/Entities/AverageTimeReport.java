/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Controllers.JobController;
import Helpers.GetListHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Daven
 */
public class AverageTimeReport {

    HashMap<String, Integer> mechanicsMap = new HashMap<>();
    List<Mechanic> mechanics = new ArrayList<>();
    Integer overall = 0;
    HashMap<String, Integer> jobTypeMap = new HashMap<>();

    public AverageTimeReport() {
        GetListHelper helper = new GetListHelper();
        List<Job> jobs = (List<Job>) helper.getListForListType(Item.ItemType.JOB);
        mechanics = (List<Mechanic>) helper.getListForListType(Item.ItemType.MECHANIC);

        HashMap<String, HashMap<String, Integer>> mechTotal = new HashMap<>();
        HashMap<String, HashMap<String, Integer>> jobTypeTotal = new HashMap<>();

        List<JobType> jobTypesArr = (List<JobType>) new GetListHelper().getListForListType(Item.ItemType.JOB_TYPE);

        jobTypesArr.stream().forEach((type) -> {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("Count", 0);
            map.put("Total", 0);
            jobTypeTotal.put(type.name(), map);
        });

        mechanics.stream().forEach((mech) -> {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("Count", 0);
            map.put("Total", 0);
            mechTotal.put("" + mech.ID() +") "+ mech.firstName() + " " + mech.lastName(), map);
        });

        JobController jobController = new JobController();

        int totalTime = 0;

        for (Job job : jobs) {

            totalTime = totalTime + job.actualTime();

            job.setMechanics(jobController.getMechanicsForJob(job));

            job.mechanics().stream().forEach((mech) -> {
                HashMap<String, Integer> map = mechTotal.get("" + mech.ID() +") "+ mech.firstName() + " " + mech.lastName());

                int initial = map.get("Total");
                int count = map.get("Count");
                map.put("Count", count + 1);
                map.put("Total", initial + mech.minsWorked());
                mechTotal.put("" + mech.ID() +") "+ mech.firstName() + " " + mech.lastName(), map);
            });

                            HashMap<String, Integer> typeMap = jobTypeTotal.get(job.jobType().name());

                int typeTotal = typeMap.get("Total");
                int typeCount = typeMap.get("Count");
                typeCount = typeCount + 1;
                typeTotal = typeTotal + job.actualTime();
                typeMap.put("Count", typeCount);
                typeMap.put("Total", typeTotal);
                jobTypeTotal.put(job.jobType().name(), typeMap);
        }

        overall = totalTime / jobs.size();

        mechTotal.keySet().stream().forEach((String key) -> {
            HashMap<String, Integer> map = mechTotal.get(key);
            int total = map.get("Total");
            int count = map.get("Count");
            int result = 0;
                        
            if (total > count && total > 0 && count > 0){
                result = total/count;
            }
                        mechanicsMap.put(key, result);
        });

        jobTypeTotal.keySet().stream().forEach((key) -> {        
            HashMap<String, Integer> map = jobTypeTotal.get(key);
            int total = map.get("Total");
            int count = map.get("Count");
            int result = 0;
                        
            if (total > count && total > 0 && count > 0){
                result = total/count;
            }
            jobTypeMap.put(key, result);
        });
    }

    /**
     * Map where average time per Mechanic is stored.
     * @return map for average time per Mechanic.
     */
    public HashMap<String, Integer> mechanicsMap() {
        return mechanicsMap;
    }

    /**
     * Overall average time across all jobs.
     * @return average time taken across all jobs.
     */
    public int overallAverage() {
        return overall;
    }

    /**
     * Map where average time per JobType is stored.
     * @return map for average time per JobType.
     */
    public HashMap<String, Integer> jobTypeMap() {
        return jobTypeMap;
    }

    /**
     * Get a list of mechanics.
     * @return list of mechanics.
     */
    public List<Mechanic> mechanics() {
        return mechanics;
    }
}
