/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModels;

import Delegates.ItemsInterface;
import Entities.Item;
import Entities.Task;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Daven
 */
public class JobTasksTableModel extends AbstractTableModel implements TableModelListener {

    List<Task> tasks = new ArrayList<>();
    ;
    Class[] columns = new Class[]{Integer.class, String.class, String.class, Integer.class};
    ItemsInterface delegate;

    public JobTasksTableModel(List<Task> tasks) {
        this.setTasks(tasks);
        addTableModelListener(this);
    }

    public JobTasksTableModel() {
    }

    public void setDelegate(ItemsInterface frame) {
        this.delegate = frame;
    }

    public void deleteTasks(int[] indexes) {

        for (int i = indexes.length - 1; i >= 0; i--) {
            System.out.println("i is " + i + " and indexes is: " + indexes[i]);
            Task task = tasks.get(indexes[i]);
            tasks.remove(task);
        }
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public int getRowCount() {
        return tasks.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
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
                return "Duration (Minutes)";
            default:
                return "Info";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columns[columnIndex];
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
        Task task = tasks.get(rowIndex);

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

        Task task = tasks.get(rowIndex);

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

        delegate.updatedItems((ArrayList<? extends Item>) tasks);
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
