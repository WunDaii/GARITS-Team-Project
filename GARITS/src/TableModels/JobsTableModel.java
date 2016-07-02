/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModels;

import Entities.Item;
import Entities.Job;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.event.TableModelEvent;

/**
 *
 * @author Daven
 */
public class JobsTableModel extends BaseTableModel {

    public JobsTableModel(List<Job> jobs) {
        super(jobs);
        init();
    }

    public JobsTableModel() {
        super();
        init();
    }

    public void init() {
        columns = new Class[]{int.class, String.class, int.class, String.class, String.class, String.class, double.class, int.class};
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "Date";
            case 2:
                return "Customer ID";
            case 3:
                return "Customer Name";
            case 4:
                return "Vehicle Reg";
            case 5:
                return "Job Type";
            case 6:
                return "Cost";
            case 7:
                return "Status";
            default:
                return "Info";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Job job = (Job) items().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return job.ID();
            case 1:
                if (job.date() != null) {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    return df.format(job.date());
                } else {
                    return "";
                }
            case 2:
                return job.customerID();
            case 3:
                return job.customer().firstName() + " " + job.customer().lastName();
            case 4:
                return job.vehicleReg();
            case 5:
                if (job.jobType() == null) {
                    return "Not stated";
                } else {
                    return job.jobType().name();
                }
            case 6:
                return job.cost();
            case 7:
                return job.status();
            default:
                return job.ID();
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
    }

    @Override
    public List<? extends Item> items() {
        return items;
    }

    @Override
    public Class[] columns() {
        return columns;
    }
}
