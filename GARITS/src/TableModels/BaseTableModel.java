/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModels;

import Entities.Item;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Daven
 */
public class BaseTableModel extends AbstractTableModel implements TableModelListener {

    List<? extends Item> items = new ArrayList<>();
    ;
    Class[] columns = new Class[]{};

    public BaseTableModel(List<? extends Item> items) {
        this.setItems(items);
        addTableModelListener(this);
    }

    public BaseTableModel() {

    }

    public void setItems(List<? extends Item> items) {
        this.items = items;
    }

    @Override
    public int getRowCount() {
        return items().size();
    }

    @Override
    public int getColumnCount() {
        return columns().length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return "None";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columns()[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return "None";
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

    public List<? extends Item> items() {
        return items;
    }

    public Class[] columns() {
        return columns;
    }
}
