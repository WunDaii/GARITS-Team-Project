/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModels;

import Entities.Task;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Daven
 */
public class TasksTableModel extends BaseTableModel {

    public TasksTableModel(List<Task> tasks) {
        super(tasks);
        init();
    }

    public TasksTableModel() {
        init();
    }

    public void init() {
        columns = new Class[]{Integer.class, String.class, String.class, Integer.class};
        addTableModelListener(this);
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Task ID";
            case 1:
                return "Name";
            case 2:
                return "Description";
            case 3:
                return "Duration";
            default:
                return "Info";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 3) {
            return true;
        }
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Task task = (Task) items().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return task.ID();
            case 1:
                return task.name();
            case 2:
                return task.description();
            case 3:
                return task.duration();
            default:
                return task.ID();
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        System.out.println("Setting value: " + aValue);
        System.out.println("columnIndex: " + columnIndex);

        Task task = (Task) items.get(rowIndex);

        switch (columnIndex) {
            case 3:
                System.out.println("columnIndex2");
                if (aValue instanceof Integer) {
                    task.setDuration((int) aValue);
                }
                break;
            default:
                return;
        }
        fireTableDataChanged();
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }

    @Override
    public void tableChanged(TableModelEvent e) {
    }
}
