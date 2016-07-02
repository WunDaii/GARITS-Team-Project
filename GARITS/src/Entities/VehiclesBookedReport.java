/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Entities.Item.ItemType;
import Helpers.GetListHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Daven
 */
public class VehiclesBookedReport {

    List<Job> jobs = new ArrayList<>();
    HashMap<String, Integer> monthly = new HashMap<>();
    HashMap<String, Integer> jobType = new HashMap<>();
    HashMap<String, Integer> customerType = new HashMap<>();
    Integer overall = 0;
    List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    public VehiclesBookedReport() {
        jobs = (List<Job>) new GetListHelper().getListForListType(ItemType.JOB);
        overall = jobs.size();

        // Setup Hashmaps
        customerType.put("Account", 0);
        customerType.put("Casual", 0);

        List<JobType> jobTypesArr = (List<JobType>) new GetListHelper().getListForListType(ItemType.JOB_TYPE);

        jobTypesArr.stream().forEach((type) -> {
            jobType.put(type.name(), 0);
        });

        months.stream().forEach((month) -> {
            monthly.put(month, 0);
        });

        // Add info to Hashmaps
        jobs.stream().map((job) -> {
            if (job.customer().customerType() == 0) {
                Integer accTotal = customerType.get("Account") + 1;
                customerType.put("Account", accTotal);
            } else {
                Integer casTotal = customerType.get("Casual") + 1;
                customerType.put("Casual", casTotal);
            }
            return job;
        }).map((job) -> {
            if (job.jobType() != null) {
                int jobTypeCount = jobType.get(job.jobType().name()) + 1;
                jobType.put(job.jobType().name(), jobTypeCount);
            }
            return job;
        }).filter((job) -> (job.date() != null)).map((job) -> {
            Calendar cal = Calendar.getInstance();
            cal.setTime(job.date());
            return cal;
        }).map((cal) -> cal.get(Calendar.MONTH)).forEach((month) -> {
            int monthTotal = monthly.get(months.get(month)) + 1;
            monthly.put(months.get(month), monthTotal);
        });
    }

    public List<String> months() {
        return months;
    }

    public HashMap<String, Integer> customerType() {
        return customerType;
    }

    public Integer overall() {
        return overall;
    }

    public HashMap<String, Integer> jobType() {
        return jobType;
    }

    public HashMap<String, Integer> monthly() {
        return monthly;
    }
}