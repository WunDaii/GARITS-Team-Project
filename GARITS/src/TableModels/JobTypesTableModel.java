/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModels;

import Entities.Item;
import Entities.JobType;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Daven
 */
public class JobTypesTableModel extends BaseTableModel {

    public JobTypesTableModel(List<JobType> users) {
        super(users);
        init();
    }

    public JobTypesTableModel() {
        super();
        init();
    }

    public void init() {
        columns = new Class[]{int.class, String.class};
        addTableModelListener(this);
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "Name";
            default:
                return "None";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        JobType jobType = (JobType) items().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return jobType.ID();
            case 1:
                return jobType.name();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        System.out.println("addTableModeListener");
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
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
