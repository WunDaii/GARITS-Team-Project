/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModels;

import Entities.Item;
import Entities.Supplier;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Daven
 */
public class SuppliersTableModel extends BaseTableModel {

    public SuppliersTableModel(List<Supplier> users) {
        super(users);
        init();
    }

    public SuppliersTableModel() {
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
                return "Name";
            case 2:
                return "Address";
            case 3:
                return "PostCode";

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
        Supplier supp = (Supplier) items().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return supp.ID();
            case 1:
                return supp.name();
            case 2:
                return supp.address();
            case 3:
                return supp.postcode();

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
