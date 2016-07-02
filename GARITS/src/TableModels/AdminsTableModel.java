/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModels;

import Entities.Item;
import Entities.User;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Daven
 */
public class AdminsTableModel extends BaseTableModel {

    public AdminsTableModel(List<User> users) {
        super(users);
        init();
    }

    public AdminsTableModel() {
        super();
        init();
    }

    public void init() {
        columns = new Class[]{int.class, String.class, String.class, String.class, int.class};
        addTableModelListener(this);
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "First Name";
            case 2:
                return "Last Name";
            case 3:
                return "Name";
            case 4:
                return "User Type";
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
        User user = (User) items().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return user.ID();
            case 1:
                return user.firstName();
            case 2:
                return user.lastName();
            case 3:
                return user.username();
            case 4:
                return user.userType();
            default:
                return user.username();
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
