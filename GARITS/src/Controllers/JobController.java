/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Job;
import Entities.JobStock;
import DatabasePackage.DBConnect;
import Entities.Mechanic;
import Entities.Task;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daven
 */
public class JobController extends BaseController {

    /**
     * Gets a list of JobStock items for the job.
     *
     * @param job the Job to get a list of stock items for.
     * @return a list of JobStock items for the Job.
     * @see Job
     * @see JobStock
     */
    public ArrayList<JobStock> getStockForJob(Job job) {

        ArrayList<JobStock> jobParts = new ArrayList<>();

        if (job.itemIDs().length() == 0 || job.itemIDs().equals("null")) {
            System.out.println("getJobPartsForJob > itemsIDs length = 0, returning");
            return jobParts;
        }

        System.out.println("Job items: " + job.itemIDs());

        List<String> jobItemStrs = Arrays.asList(job.itemIDs().split(","));
        Map<String, Integer> partsMap = new HashMap<>();

        for (String string : jobItemStrs) {
            List<String> jobItemStr_ = Arrays.asList(string.split("="));
            String jobItemID = jobItemStr_.get(0);
            Integer quantity = Integer.parseInt(jobItemStr_.get(1));
            partsMap.put(jobItemID, quantity);
        }

        ArrayList<String> whereStrs = new ArrayList<>();

        partsMap.keySet().stream().forEach((key) -> {
            whereStrs.add("STOCK.ITEM_NUMBER='" + key + "'");
        });

        String whereIDs = String.join(" OR ", whereStrs);

        String query = "SELECT STOCK.*, SPARE_PARTS.*, SUPPLIERS.* FROM STOCK LEFT OUTER JOIN SPARE_PARTS ON STOCK.ITEM_NUMBER=SPARE_PARTS.ITEM_NUMBER "
                + "LEFT OUTER JOIN SUPPLIERS ON SPARE_PARTS.SUPPLIER_ID=SUPPLIERS.SUPPLIER_ID "
                + "WHERE " + whereIDs;

        System.out.println("QUERY: " + query);

        ResultSet rs = DBConnect.sharedInstance().executeSelectQuery(query);

        try {

            while (rs.next()) {
                String partID = rs.getString("ITEM_NUMBER");
                JobStock jobPart = new JobStock(rs, partsMap.get(partID));
                jobParts.add(jobPart);
            }

        } catch (SQLException ex) {
            Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jobParts;
    }

    /**
     * Gets a list of Task items for the job.
     *
     * @param job the Job to get a list of Tasks for.
     * @return a list of Tasks for the Job.
     * @see Job
     * @see Task
     */
    public ArrayList<Task> getTasksForJob(Job job) {

        ArrayList<Task> jobTasks = new ArrayList<Task>();

        if (job.taskIDs().length() == 0 || job.taskIDs().equals("null")) {
            return jobTasks;
        }

        List<String> jobTaskStrs = Arrays.asList(job.taskIDs().split(","));
        Map<String, Integer> tasksMap = new HashMap<>();

        for (String string : jobTaskStrs) {
            List<String> jobTaskStr_ = Arrays.asList(string.split("="));
            String jobTaskID = jobTaskStr_.get(0);
            Integer quantity = Integer.parseInt(jobTaskStr_.get(1));
            tasksMap.put(jobTaskID, quantity);
        }

        ArrayList<String> whereStrs = new ArrayList<String>();

        for (String key : tasksMap.keySet()) {
            whereStrs.add("TASK_ID=" + key);
        }

        String whereIDs = String.join(" OR ", whereStrs);

        String query = "SELECT * FROM TASKS WHERE " + whereIDs;

        ResultSet rs = DBConnect.sharedInstance().executeSelectQuery(query);

        try {

            while (rs.next()) {
                String partID = rs.getString("TASK_ID");
                Task jobTask = new Task(rs, tasksMap.get(partID));
                jobTasks.add(jobTask);
            }

        } catch (SQLException ex) {
            Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jobTasks;
    }

    /**
     * Gets a list of Mechanics for the job.
     *
     * @param job the Job to get a list of Mechanics for.
     * @return a list of Mechanics for the Job.
     * @see Job
     * @see Mechanic
     */
    public ArrayList<Mechanic> getMechanicsForJob(Job job) {

        ArrayList<Mechanic> mechs = new ArrayList<Mechanic>();

        if (job.mechanicIDs() == null || job.mechanicIDs().length() == 0 || job.mechanicIDs().equals("null") || job.mechanicIDs().equals("")) {
            return mechs;
        }

        List<String> mechStrs = Arrays.asList(job.mechanicIDs().split(","));
        Map<String, Integer> mechsMap = new HashMap<>();

        for (String string : mechStrs) {
            List<String> jobTaskStr_ = Arrays.asList(string.split("="));
            String mechID = jobTaskStr_.get(0);
            Integer minsWorked = Integer.parseInt(jobTaskStr_.get(1));
            mechsMap.put(mechID, minsWorked);
        }

        ArrayList<String> whereStrs = new ArrayList<String>();

        for (String key : mechsMap.keySet()) {
            whereStrs.add("USER_ID=" + key);
        }

        String whereIDs = String.join(" OR ", whereStrs);

        String query = "SELECT * FROM USERS "
                + "WHERE " + whereIDs;

        ResultSet rs = DBConnect.sharedInstance().executeSelectQuery(query);

        try {

            while (rs.next()) {
                Mechanic mech = new Mechanic(rs);
                String key = "" + mech.ID();
                mech.setMinutesWorked(mechsMap.get(key));
                mechs.add(mech);
            }

        } catch (SQLException ex) {
            Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mechs;
    }

    /**
     * Updates the Job item in the database. Updates the Job's details and the
     * Stock's available quantity.
     *
     * @param job the Job's details to update.
     * @see Job
     */
    public void updateJob(Job job) {
        super.updateItem(job);
        List<JobStock> stocks = job.items();

        for (JobStock stock : stocks) {
            super.updateItem(stock);
        }
    }

    /**
     * Adds a Job to the database. Adds a new Job and updates the Stock's (if
     * any) quantity in the database.
     *
     * @param job The Job to add.
     * @return whether Job has successfully been added to the database.
     * @see Job
     */
    public boolean addJob(Job job) {
        if (super.addItem(job)) {
            List<JobStock> stocks = job.items();

            if (stocks != null) {
                for (JobStock stock : stocks) {
                    super.updateItem(stock);
                }
            }
            return true;
        } else {
            return false;
        }
    }
}