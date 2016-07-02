/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModels;

import Delegates.ItemsInterface;
import Entities.Item;
import Entities.JobStock;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Daven
 */
public class JobPartsTableModel extends BaseTableModel {

    ItemsInterface delegate;

    public JobPartsTableModel(List<JobStock> parts) {
        super(parts);
        init();
    }

    public JobPartsTableModel() {
        init();
    }

    public void setDelegate(ItemsInterface delegate) {
        this.delegate = delegate;
    }

    public void init() {
        columns = new Class[]{String.class, String.class, String.class, Integer.class, Integer.class};
        addTableModelListener(this);
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Part Number";
            case 1:
                return "Name";
            case 2:
                return "Cost";
            case 3:
                return "Quantity";
            case 4:
                return "Available Quantity";
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
        JobStock part = (JobStock) items().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return part.part().number();
            case 1:
                return part.part().name();
            case 2:
                return String.format("£%.2f", part.cost());
            case 3:
                return part.jobQuantity();
            case 4:
                return part.quantity();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        JobStock part = (JobStock) items().get(rowIndex);

        switch (columnIndex) {
            case 3:
                if (aValue instanceof Integer) {
                    part.setJobQuantity((int) aValue);
                }
                break;
            default:
                return;
        }

        this.delegate.updatedItems((ArrayList<? extends Item>) items());
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

    @Override
    public List<? extends Item> items() {
        return items;
    }

    @Override
    public Class[] columns() {
        return columns;
    }
}
