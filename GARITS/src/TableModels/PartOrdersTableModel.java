/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModels;

import Entities.Item;
import Entities.PartOrder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Daven
 */
public class PartOrdersTableModel extends BaseTableModel {

    public PartOrdersTableModel(List<PartOrder> orders) {
        super(orders);
        init();
    }

    public PartOrdersTableModel() {
        super();
        init();
    }

    public void init() {
        columns = new Class[]{int.class, String.class, String.class, String.class};
        addTableModelListener(this);
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "Date";
            case 2:
                return "Customer";
            case 3:
                return "Cost";
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
        PartOrder order = (PartOrder) items().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return order.ID();
            case 1:
                if (order.date() != null) {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    return df.format(order.date());
                } else {
                    return "";
                }
            case 2:
                return order.customer().firstName() + " " + order.customer().lastName();
            case 3:
                return String.format("£%.2f", order.cost());
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
