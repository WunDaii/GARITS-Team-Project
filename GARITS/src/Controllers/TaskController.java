/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Task;
import java.sql.SQLException;

/**
 *
 * @author Daven
 */
public class TaskController extends BaseController{
    
    /**
     * Updates a Task's details in the database.
     * Returns true if the task has successfully been updated, or false if a task with
     * the same name already exists.
     * @param task the task to update.
     * @return if the Task has successfully been added to the database.
     * @see Task
     */
            public boolean updateTask(Task task) {
                
            try {
                if (alreadyExists(task)){
                    return false;
                }
                
                super.updateItem(task);
                return true;
            } catch (SQLException ex) {
                return false;
            }
        }
}
